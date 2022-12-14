package vn.com.tpf.microservices.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.tpf.microservices.extensions.HttpServletRequestWrapperExtension;
import vn.com.tpf.microservices.extensions.HttpServletResponseWrapperExtension;
import vn.com.tpf.microservices.services.RabbitMQService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class PGPFilter implements Filter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper mapper;
	private RabbitMQService rabbitMQService;

	public PGPFilter(ObjectMapper mapper, RabbitMQService rabbitMQService) {
		super();
		this.mapper = mapper;
		this.rabbitMQService = rabbitMQService;
	}

	private String convertBufferedReadertoString(BufferedReader bufferedReader) throws IOException {
		int intValueOfChar;
		String targetString = "";
		while ((intValueOfChar = bufferedReader.read()) != -1) {
			targetString += (char) intValueOfChar;
		}
		return targetString.replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (request.getUserPrincipal() != null && request.getContentType() != null
				&& request.getContentType().equals("application/pgp-encrypted")) {
			HttpServletRequestWrapperExtension req = new HttpServletRequestWrapperExtension(request);
			HttpServletResponseWrapperExtension res = new HttpServletResponseWrapperExtension(response);

			String data = convertBufferedReadertoString(request.getReader());
			JsonNode body = mapper.createObjectNode();
			String[] projects = request.getUserPrincipal().getName().split("-");
			String project = projects[projects.length - 1];
			String respString = "";

			try {
				JsonNode decrypt = rabbitMQService.sendAndReceive("tpf-service-assets",
						Map.of("func", "pgpDecrypt", "body", Map.of("project", project, "data", data)));
				if (decrypt.path("data").isObject() && Integer.valueOf(decrypt.path("status").asInt()).equals(200)) {
					body = decrypt.path("data");
					req.updateInputStream(body.toString().getBytes());
					chain.doFilter(req, res);
					respString = res.getContent();
				} else
					respString = mapper.writeValueAsString(
							Map.of("request_id", "", "reference_id", "", "result_code", 499, "message", "Pgp error"));
			} catch (Exception e) {
				log.error("{} {}", data, e.getMessage());
			}

			try {
				JsonNode encrypt = rabbitMQService.sendAndReceive("tpf-service-assets",
						Map.of("func", "pgpEncrypt", "body", Map.of("project", project, "data", respString)));
				respString = encrypt.path("data").asText();
			} catch (Exception e) {
				log.error("{} {}", respString, e.getMessage());
//				e.printStackTrace();
			}

			PrintWriter printWriter = servletResponse.getWriter();
			printWriter.write(respString);
			printWriter.flush();
			printWriter.close();
		} else {
			chain.doFilter(request, response);
		}
	}

}