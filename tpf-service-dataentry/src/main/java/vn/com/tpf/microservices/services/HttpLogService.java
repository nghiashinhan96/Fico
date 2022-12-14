package vn.com.tpf.microservices.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpLogService implements ClientHttpRequestInterceptor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		log.info("[==HTTP-LOG-REQUEST==] : {}", Map.of("payload", new String(body, "UTF-8"), "method", request.getMethod(),
				"url", request.getURI(), "header", request.getHeaders()));

		ClientHttpResponse response = execution.execute(request, body);

		log.info("[==HTTP-LOG-RESPONSE==] : {}", Map.of("payload", new String(body, "UTF-8"), "status",
				response.getStatusCode(), "result", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset())));

		return response;
	}

}