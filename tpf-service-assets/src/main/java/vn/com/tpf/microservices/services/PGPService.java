package vn.com.tpf.microservices.services;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.tpf.microservices.utils.PGPHelper;
import vn.com.tpf.microservices.utils.PGPInfo;

@Service
public class PGPService {

	@Autowired
	private ObjectMapper mapper;
	
//	
//	@PostConstruct
//	public void initTest() throws JsonProcessingException {
//		
//		String data = "-----BEGIN PGP MESSAGE-----\r\n" + 
//				"\r\n" + 
//				"hIwD9/nZHotASLUBBADCOJtDlKYeHbjB1aEHuGWy9DNZ+wjsDs4C5LUqaJ06rQNn\r\n" + 
//				"v3xsSvRtYkzfk9pqaGK87i16pDUvodpjYnwLYFj/1tG3uLX9uKXFI9bioNoSBdWw\r\n" + 
//				"lkRpJxy8R8tfXXdMa58qKcammp011JborbrqFo10OlQWRRDtonl32aRDtIDcU9JJ\r\n" + 
//				"ASg/4OSj/UxwlSMrHM1ekVAufshiNAkbgd8NdaDgJs1iFFp7ijwAeHTJIsRgt18Y\r\n" + 
//				"ealcAnX1KaYHNKWTYPR9fFMmDDdZbak47g==\r\n" + 
//				"=yxI2\r\n" + 
//				"-----END PGP MESSAGE-----";
//	
////		JsonNode response  = pgpEncrypt(mapper.convertValue(Map.of("func", "pgpDecrypt", "body", Map.of("project", "smartnet", "data", "000111222333")),JsonNode.class));
//		JsonNode response  = pgpDecrypt(mapper.convertValue(Map.of("func", "pgpDecrypt", "body", Map.of("project", "smartnet", "data", data)),JsonNode.class));
//			
//		System.out.println(mapper.writeValueAsString(response));
////		System.out.println(PGPInfo.publicKey);
//	}

	public JsonNode pgpEncrypt(JsonNode request) {
		String preshareKey = PGPInfo.preshareKey;
		String privateKey = PGPInfo.privateKey;
		String publicKey = PGPInfo.publicKey;

		if (request.path("body").path("preshareKey").isTextual()) {
			preshareKey = request.path("body").path("preshareKey").asText();
		}
		if (request.path("body").path("privateKey").isTextual()) {
			privateKey = request.path("body").path("privateKey").asText();
		}
		if (request.path("body").path("publicKey").isTextual()) {
			publicKey = request.path("body").path("publicKey").asText();
		}
		if (request.path("body").path("project").isTextual()) {
			publicKey = PGPInfo.publicKey3P.get(request.path("body").path("project").asText());
		}

		PGPHelper pgpHelper = new PGPHelper(preshareKey, privateKey, publicKey);
		ByteArrayOutputStream encStream = new ByteArrayOutputStream();
		pgpHelper.encryptAndSign(request.path("body").path("data").asText().getBytes(), encStream);
		return mapper.createObjectNode().put("status", 200).put("data", encStream.toString());
	}

	public JsonNode pgpDecrypt(JsonNode request) {
		String preshareKey = PGPInfo.preshareKey;
		String privateKey = PGPInfo.privateKey;
		String publicKey = PGPInfo.publicKey;

		if (request.path("body").path("preshareKey").isTextual()) {
			preshareKey = request.path("body").path("preshareKey").asText();
		}
		if (request.path("body").path("privateKey").isTextual()) {
			privateKey = request.path("body").path("privateKey").asText();
		}
		if (request.path("body").path("publicKey").isTextual()) {
			publicKey = request.path("body").path("publicKey").asText();
		}
		if (request.path("body").path("project").isTextual()) {
			publicKey = PGPInfo.publicKey3P.get(request.path("body").path("project").asText());
		}

		PGPHelper pgpHelper = new PGPHelper(preshareKey, privateKey, publicKey);
		ByteArrayOutputStream desStream = new ByteArrayOutputStream();
		try {
			pgpHelper.decryptAndVerifySignature(request.path("body").path("data").asText().replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n").getBytes(), desStream);
			return mapper.createObjectNode().put("status", 200).set("data", mapper.readTree(desStream.toString()));
		} catch (Exception e) {
			return mapper.createObjectNode().put("status", 500).put("message", e.getMessage());
		}
	}

}
