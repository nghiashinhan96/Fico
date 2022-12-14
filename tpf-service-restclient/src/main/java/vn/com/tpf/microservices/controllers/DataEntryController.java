package vn.com.tpf.microservices.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import vn.com.tpf.microservices.services.RabbitMQService;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class DataEntryController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RabbitMQService rabbitMQService;

	@Value("${spring.url.uploadfile}")
	private String urlUploadfile;

	@Value("${spring.url.digitex-documentapi}")
	private String urlDigitexDocumentApi;

	@Value("${spring.url.digitex-resumitdocumentapi}")
	private String urlDigitexResumitDocumentApi;

	@Value("${spring.url.digitex-token}")
	private String digitexToken;

	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		restTemplate = new RestTemplate(factory);
	}

	@PostMapping("/v1/dataentry/addproduct")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "addProduct");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/addproductv2")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> addProductV2(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "addProductV2");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getproductbyname")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getProductByName(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getProductByName");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getproductbynamev2")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getProductByNameV2(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getProductByNameV2");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getproductbynamev2_test")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getProductByNameV2Test(@RequestHeader("Authorization") String token,
													@RequestPart("description")  String description)
			throws Exception {


		ObjectMapper mapper = new ObjectMapper();


		Map<String, Object> request = new HashMap<>();
		request.put("func", "getProductByNameV2");
		request.put("token", token);
		request.put("body",  Map.of("request_id", "d062b2f8-dc", "date_time", "1565667082",
				"data", Map.of("aaaaa", "d062b2f8-dc")));

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);

		JsonNode dataDescription = mapper.readTree(description);
		JsonNode jjj = mapper.createArrayNode();

		((ArrayNode) jjj).add(dataDescription.get(0));
		try {
			for (JsonNode node : dataDescription){
				for (JsonNode item : jjj) {
					if (!node.path("file-name").textValue().equals(item.path("file-name").textValue())){
						((ArrayNode) jjj).add(node);
						break;
					}
				}
			}
		}catch (Exception ex){
			return ResponseEntity.status(response.path("status").asInt(200))
					.header("x-pagination-total", response.path("total").asText("0")).body(ex);
		}


		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getall")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getAll");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getappid")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getByAppId(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getByAppId");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getaddress")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getAddress(@RequestHeader("Authorization") String token)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getAddress");
		request.put("token", token);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getbranch")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getBranch(@RequestHeader("Authorization") String token)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getBranch");
		request.put("token", token);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getbranchbyuser")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getBranchByUser(@RequestHeader("Authorization") String token)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getBranchByUser");
		request.put("token", token);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/firstcheck")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> firstCheck(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "firstCheck");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}


	@PostMapping("/v1/dataentry/sendapp")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> sendapp(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "sendApp");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/updateapp")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> updateapp(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "updateApp");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/old/dataentry/commentapp")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> comment(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "commentApp");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/updatestatus")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> cancel(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "cancelApp");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/old/dataentry/quicklead")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> quicklead(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "quickLead");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/old/dataentry/uploadfile/{appId}")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> uploadfile(@RequestHeader("Authorization") String token,
										@RequestPart("files")  MultipartFile[] files,
										@PathVariable String appId)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MultiValueMap<String, Object> parts_02 =
				new LinkedMultiValueMap<String, Object>();
		JsonNode body = null;
		request.put("func", "uploadFile");
		request.put("token", token);
		request.put("appId", appId);

		boolean validateIdCard = false;
		boolean validateHousehold = false;
		boolean validatePersonalImage = false;
		boolean validateACCA = false;
		boolean validateComment = false;

        if (appId.equals("new")) {

            for (MultipartFile item : files) {
                if (item.getOriginalFilename().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
                    validateIdCard = true;
                } else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
                    validateIdCard = true;
                } else if (item.getOriginalFilename().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
                    validateHousehold = true;
                } else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
                    validateHousehold = true;
                } else if (item.getOriginalFilename().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())) {
                    validatePersonalImage = true;
                } else if (item.getOriginalFilename().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())) {
                    validateACCA = true;
                }
            }
            if (validateIdCard && validateHousehold && validatePersonalImage && validateACCA) {
            } else {
                return ResponseEntity.status(200)
                        .header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
                                "result_code", 3, "message", "Thieu document!"));
            }
        }else {
			for (MultipartFile item : files) {
				if (item.getOriginalFilename().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())) {
					validatePersonalImage = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())) {
					validateACCA = true;
				}else {
					validateComment = true;
				}
			}
			if (!validateComment) {
			} else {
				if (validateIdCard || validateHousehold || validatePersonalImage || validateACCA){
					return ResponseEntity.status(200)
							.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
									"result_code", 3, "message", "Document khong hop le!"));
				}else {
					return ResponseEntity.status(200)
							.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
									"result_code", 3, "message", "Sai Document!"));
				}
			}
		}

		try {
			ResponseEntity<?> res = new ResponseEntity<Authenticator.Success>(HttpStatus.CREATED);

			MultiValueMap<String, Object> parts =
					new LinkedMultiValueMap<String, Object>();
			for (MultipartFile item:
					files) {
				parts.add("file", item.getResource());
			}
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				HttpEntity<?> entity = new HttpEntity<>(parts, headers);
				res = restTemplate.postForEntity(urlUploadfile, entity, List.class);

				ObjectNode dataLog = mapper.createObjectNode();
				dataLog.put("type", "[==HTTP-LOG==]");
				dataLog.set("result", mapper.convertValue(res, JsonNode.class));
				log.info("{}", dataLog);
			}
			catch (Exception e) {
				ObjectNode dataLog = mapper.createObjectNode();
				dataLog.put("type", "[==HTTP-LOG==]");
				dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
				log.error("{}", dataLog);

				int i=0;
				do {
					Thread.sleep(30000);
					try{
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						HttpEntity<?> entity = new HttpEntity<>(parts, headers);
						res = restTemplate.postForEntity(urlUploadfile, entity, List.class);
						break;
					} catch (Exception ex) {
					}
					i = i +1;
				}while(i<2);
			}

			if (res.getStatusCodeValue() == 200){
				JsonNode outputDT = null;
				body = mapper.valueToTree(res.getBody());
				boolean checkIdCard = false;
				boolean checkHousehold = false;
				int i = 0;

				if (appId.equals("new")){
					ArrayNode documents = mapper.createArrayNode();
					if(files != null) {

						for (JsonNode item :body){
//							i = 0;
							ObjectNode doc = mapper.createObjectNode();

//							if (item.path("originalname").textValue().equals("TPF_ID Card.pdf") || item.path("originalname").textValue().equals("TPF_Notarization of ID card.pdf")){
//								doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
//								doc.put("md5", item.path("md5").textValue());
//								documents.add(doc);
//
//								MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
//										"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
//								parts_02.add("ID-Card", multipartFileToSend.getResource());
//							}else if (item.path("originalname").textValue().equals("TPF_Family Book.pdf") || item.path("originalname").textValue().equals("TPF_Notarization of Family Book.pdf")){
//								doc.put("file-name", "Household_" + item.path("originalname").textValue());
//								doc.put("md5", item.path("md5").textValue());
//								documents.add(doc);
//
//								MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
//										"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
//								parts_02.add("Household", multipartFileToSend.getResource());
//							}else
							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
								doc.put("file-name", "Personal-Image_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("Personal-Image_" + files[i].getOriginalFilename(),
										"Personal-Image_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("Personal-Image", multipartFileToSend.getResource());
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
								doc.put("file-name", "ACCA-Form_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("ACCA-Form_" + files[i].getOriginalFilename(),
										"ACCA-Form_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("ACCA-Form", multipartFileToSend.getResource());
							}
							i = i + 1;
						}
                        i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())){
							    if (!checkIdCard) {
                                    doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
                                    doc.put("md5", item.path("md5").textValue());
                                    documents.add(doc);

                                    MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
                                            "ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
                                    parts_02.add("ID-Card", multipartFileToSend.getResource());

                                    checkIdCard = true;
                                }
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())){
							    if (!checkHousehold) {
                                    doc.put("file-name", "Household_" + item.path("originalname").textValue());
                                    doc.put("md5", item.path("md5").textValue());
                                    documents.add(doc);

                                    MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
                                            "Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
                                    parts_02.add("Household", multipartFileToSend.getResource());

                                    checkHousehold = true;
                                }
							}
							i = i + 1;
						}
                        i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())){
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())){
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}
						parts_02.add("description", Map.of("files", documents));
					}
					try {
						if (parts_02.size() > 0) {
                            try{
                                ObjectNode dataLogReq = mapper.createObjectNode();
                                dataLogReq.put("type", "[==HTTP-LOG-REQUEST==DIGITEXX==]");
                                dataLogReq.put("method", "POST");
                                dataLogReq.put("url", urlDigitexDocumentApi);
								dataLogReq.put("data", parts_02.toString());
                                log.info("{}", dataLogReq);
                            }
                            catch (Exception ex){}


							HttpHeaders headers_DT = new HttpHeaders();
							headers_DT.set("authkey", digitexToken);
							headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
							HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);
							ResponseEntity<?> res_DT = restTemplate.postForEntity(urlDigitexDocumentApi, entity_DT, Object.class);

							Object map = mapper.valueToTree(res_DT.getBody());
							outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));

//						Map<String, List> map = mapper.readValue(res_DT.getBody().toString(), new TypeReference<Map<String, List>>() {});
//						outputDT = mapper.readTree(mapper.writeValueAsString(map.get("output")));

							ObjectNode dataLog = mapper.createObjectNode();
							dataLog.put("type", "[==HTTP-LOG-RESPONSE==DIGITEXX==]");
							dataLog.put("url", urlDigitexDocumentApi);
							dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
							log.info("{}", dataLog);

						}
					}
					catch (Exception e) {
						ObjectNode dataLog = mapper.createObjectNode();
						dataLog.put("type", "[==HTTP-LOG==]");
						dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
						log.error("{}", dataLog);

//						int i=0;
//						do {
////							Thread.sleep(30000);
//							try{
//								HttpHeaders headers_DT = new HttpHeaders();
//								headers_DT.set("authkey", digitexToken);
//								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
//								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);
//								ResponseEntity<?> res_DT = restTemplate.postForEntity(urlDigiTex, entity_DT, Object.class);
//
//								Object map = mapper.valueToTree(res_DT.getBody());
//								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));
//
//								break;
//							} catch (Exception ex) {
//								dataLog = mapper.createObjectNode();
//								dataLog.put("type", "[==HTTP-LOG==]");
//								dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
//								log.error("{}", dataLog);
//							}
//							i = i +1;
//						}while(i<2);
					}

				} else{
					Map<String, Object> request_docId = new HashMap<>();
					request_docId.put("func", "getDocumentId");
					request_docId.put("token", token);
					request_docId.put("appId", appId);
					JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request_docId);
					int countDocId = response.path("data").path("data").size();

					ArrayNode documents = mapper.createArrayNode();
					if(files != null) {
						for (JsonNode item :body){
//							i = 0;
							ObjectNode doc = mapper.createObjectNode();

//							if (item.path("originalname").textValue().equals("TPF_ID Card.pdf") || item.path("originalname").textValue().equals("TPF_Notarization of ID card.pdf")){
//								doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
//								doc.put("md5", item.path("md5").textValue());
//								String docId = null;
//								for (int j = 0; j < countDocId; j++){
//									if (response.path("data").path("data").get(j).path("originalname").textValue().equals("TPF_ID Card.pdf") ||
//											response.path("data").path("data").get(j).path("originalname").textValue().equals("TPF_Notarization of ID card.pdf")){
//										docId = response.path("data").path("data").get(j).path("urlid").textValue();
//									}
//								}
//								doc.put("document-id", docId);
//								documents.add(doc);
//
//								MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
//										"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
//								parts_02.add("ID-Card", multipartFileToSend.getResource());
//							}else if (item.path("originalname").textValue().equals("TPF_Family Book.pdf") || item.path("originalname").textValue().equals("TPF_Notarization of Family Book.pdf")){
//								doc.put("file-name", "Household_" + item.path("originalname").textValue());
//								doc.put("md5", item.path("md5").textValue());
//								String docId = null;
//								for (int j = 0; j < countDocId; j++){
//									if (response.path("data").path("data").get(j).path("originalname").textValue().equals("TPF_Family Book.pdf") ||
//											response.path("data").path("data").get(j).path("originalname").textValue().equals("TPF_Notarization of Family Book.pdf")){
//										docId = response.path("data").path("data").get(j).path("urlid").textValue();
//									}
//								}
//								doc.put("document-id", docId);
//								documents.add(doc);
//
//								MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
//										"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
//								parts_02.add("Household", multipartFileToSend.getResource());
//							}else
							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
								doc.put("file-name", "Personal-Image_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								String docId = null;
								for (int j = 0; j < countDocId; j++){
									if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
										docId = response.path("data").path("data").get(j).path("urlid").textValue();
									}
								}
								doc.put("document-id", docId);
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("Personal-Image_" + files[i].getOriginalFilename(),
										"Personal-Image_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("Personal-Image", multipartFileToSend.getResource());
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
								doc.put("file-name", "ACCA-Form_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								String docId = null;
								for (int j = 0; j < countDocId; j++){
									if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
										docId = response.path("data").path("data").get(j).path("urlid").textValue();
									}
								}
								doc.put("document-id", docId);
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("ACCA-Form_" + files[i].getOriginalFilename(),
										"ACCA-Form_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("ACCA-Form", multipartFileToSend.getResource());
							}
							i = i + 1;
						}

                        i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())){
							    if (!checkIdCard) {
                                    doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
                                    doc.put("md5", item.path("md5").textValue());
                                    String docId = null;
                                    for (int j = 0; j < countDocId; j++) {
                                        if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
                                            docId = response.path("data").path("data").get(j).path("urlid").textValue();
                                        }
                                    }
                                    doc.put("document-id", docId);
                                    documents.add(doc);

                                    MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
                                            "ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
                                    parts_02.add("ID-Card", multipartFileToSend.getResource());

                                    checkIdCard = true;
                                }
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())){
							    if (!checkHousehold) {
                                    doc.put("file-name", "Household_" + item.path("originalname").textValue());
                                    doc.put("md5", item.path("md5").textValue());
                                    String docId = null;
                                    for (int j = 0; j < countDocId; j++) {
                                        if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
                                            docId = response.path("data").path("data").get(j).path("urlid").textValue();
                                        }
                                    }
                                    doc.put("document-id", docId);
                                    documents.add(doc);

                                    MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
                                            "Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
                                    parts_02.add("Household", multipartFileToSend.getResource());

                                    checkHousehold = true;
                                }
							}
							i = i + 1;
						}

                        i = 0;
						for (JsonNode item :body) {

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}

							} else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}
						parts_02.add("description", Map.of("files", documents));
					}
					try {
						if (parts_02.size() > 0) {
						    try{
                                ObjectNode dataLogReq = mapper.createObjectNode();
                                dataLogReq.put("type", "[==HTTP-LOG-REQUEST==DIGITEXX==]");
                                dataLogReq.put("method", "POST");
                                dataLogReq.put("appId", appId);
                                dataLogReq.put("url", urlDigitexResumitDocumentApi);
								dataLogReq.put("data", parts_02.toString());
                                log.info("{}", dataLogReq);
                            }
                            catch (Exception ex){}

							HttpHeaders headers_DT = new HttpHeaders();
							headers_DT.set("authkey", digitexToken);
							headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
							HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);
							ResponseEntity<?> res_DT = restTemplate.postForEntity(urlDigitexResumitDocumentApi, entity_DT, Object.class);

//						Map<String, List> map = mapper.readValue(res_DT.getBody().toString().replaceAll("\"\\{\\[","\\[").replaceAll("\\]\\}\"","\\]"), new TypeReference<Map<String, List>>() {});
//						outputDT = mapper.readTree(mapper.writeValueAsString(map.get("output")));

							Object map = mapper.valueToTree(res_DT.getBody());
							outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));

							ObjectNode dataLog = mapper.createObjectNode();
							dataLog.put("type", "[==HTTP-LOG-RESPONSE==DIGITEXX==]");
							dataLog.put("url", urlDigitexResumitDocumentApi);
							dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
							log.info("{}", dataLog);
						}
					}
					catch (Exception e) {
						ObjectNode dataLog = mapper.createObjectNode();
						dataLog.put("type", "[==HTTP-LOG==]");
						dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
						log.error("{}", dataLog);

//						int i=0;
//						do {
////							Thread.sleep(30000);
//							try{
//								HttpHeaders headers_DT = new HttpHeaders();
//								headers_DT.set("authkey", digitexToken);
//								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
//								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);
//								ResponseEntity<?> res_DT = restTemplate.postForEntity(urlDigiTexResubmit, entity_DT, Object.class);
//
//								Object map = mapper.valueToTree(res_DT.getBody());
//								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));
//
//								break;
//							} catch (Exception ex) {
//								dataLog = mapper.createObjectNode();
//								dataLog.put("type", "[==HTTP-LOG==]");
//								dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
//								log.error("{}", dataLog);
//							}
//							i = i +1;
//						}while(i<2);
					}
				}
				if (outputDT == null){
					JsonNode jNode = mergeFile(body, null);
					request.put("body", jNode);
					request.put("uploadDigiTex", "FAIL");

//					return ResponseEntity.status(200)
//							.header("x-pagination-total", "0").body(Map.of("reference_id",UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
//									"result_code", 2, "message", "uploadFile DigiTex fail!", "data", body));

//					request.put("body", body);
				}else {
					JsonNode jNode = mergeFile(body, mapper.valueToTree(outputDT));
					request.put("body", jNode);
				}
			}else{
				return ResponseEntity.status(200)
						.header("x-pagination-total", "0").body(Map.of("reference_id",UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
								"result_code", 1, "message", "uploadFile TPF fail!"));
			}

		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(500)
					.header("x-pagination-total", "0").body(e.toString());
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.header("x-pagination-total", "0").body(e.toString());
		}

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/old/dataentry/uploaddigitex")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> uploadDigiTex(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "uploadDigiTex");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
 		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/gettatreport")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getTATReport(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getTATReport");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=TATReport_"+new Date().getTime()+".xlsx");

		Base64.Decoder dec = Base64.getDecoder();
		byte[] decbytes = dec.decode(response.path("data").asText());

		ByteArrayInputStream in = new ByteArrayInputStream(decbytes);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));
	}

	@RequestMapping("/v1/dataentry/getstatusreport")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getStatusReport(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getStatusReport");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);

		Base64.Decoder dec = Base64.getDecoder();
		byte[] decbytes = dec.decode(response.path("data").asText());
		ByteArrayInputStream in = new ByteArrayInputStream(decbytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=StatusReport_"+new Date().getTime()+".xlsx");

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename)
				.filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".")));
	}

	public JsonNode mergeFile(JsonNode mainNode, JsonNode updateNode) {
	    boolean checkIdCard = false;
        boolean checkHousehold = false;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultNode = mapper.createArrayNode();
		for (JsonNode item : mainNode) {
			if (updateNode != null) {
				for (JsonNode item2 : updateNode) {
					if (item.findPath("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase()) ||
							item.findPath("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
						if (item2.findPath("document-type").textValue().equals("ID-Card")) {
						    if (!checkIdCard) {
                                ObjectNode doc = mapper.createObjectNode();
                                doc.put("originalname", item.findPath("originalname").textValue());
                                doc.put("filename", item.findPath("filename").textValue());
                                doc.put("md5", item.findPath("md5").textValue());
                                doc.put("urlid", item2.findPath("document-id").asText(null));
                                ((ArrayNode) resultNode).add(doc);

                                checkIdCard=true;
                            }
						}
					} else if (item.findPath("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase()) ||
							item.findPath("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
						if (item2.findPath("document-type").textValue().equals("Household")) {
                            if (!checkHousehold) {
                                ObjectNode doc = mapper.createObjectNode();
                                doc.put("originalname", item.findPath("originalname").textValue());
                                doc.put("filename", item.findPath("filename").textValue());
                                doc.put("md5", item.findPath("md5").textValue());
                                doc.put("urlid", item2.findPath("document-id").asText(null));
                                ((ArrayNode) resultNode).add(doc);

                                checkHousehold = true;
                            }
						}
					} else if (item.findPath("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())) {
						if (item2.findPath("document-type").textValue().equals("Personal-Image")) {
							ObjectNode doc = mapper.createObjectNode();
							doc.put("originalname", item.findPath("originalname").textValue());
							doc.put("filename", item.findPath("filename").textValue());
							doc.put("md5", item.findPath("md5").textValue());
							doc.put("urlid", item2.findPath("document-id").asText(null));
							((ArrayNode) resultNode).add(doc);
						}
					} else if (item.findPath("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())) {
						if (item2.findPath("document-type").textValue().equals("ACCA-Form")) {
							ObjectNode doc = mapper.createObjectNode();
							doc.put("originalname", item.findPath("originalname").textValue());
							doc.put("filename", item.findPath("filename").textValue());
							doc.put("md5", item.findPath("md5").textValue());
							doc.put("urlid", item2.findPath("document-id").asText(null));
							((ArrayNode) resultNode).add(doc);
						}
					} else {
						ObjectNode doc = mapper.createObjectNode();
						doc.put("originalname", item.findPath("originalname").textValue());
						doc.put("filename", item.findPath("filename").textValue());
						doc.put("md5", item.findPath("md5").textValue());
						((ArrayNode) resultNode).add(doc);

						break;
					}

//				if (item.findPath("originalname").textValue().equals(item2.findPath("originalname").textValue())){
//					ObjectNode doc = mapper.createObjectNode();
//					doc.put("originalname", item.findPath("originalname").textValue());
//					doc.put("filename", item.findPath("filename").textValue());
//					doc.put("urlid", item2.findPath("urlId").textValue());
//					((ArrayNode) resultNode).add(doc);
//				}
				}
			}else{
				ObjectNode doc = mapper.createObjectNode();
				doc.put("originalname", item.findPath("originalname").textValue());
				doc.put("filename", item.findPath("filename").textValue());
				doc.put("md5", item.findPath("md5").textValue());
				((ArrayNode) resultNode).add(doc);
			}
		}
		return resultNode;
	}

	@PostMapping("/v1/dataentry/autoassign")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> autoAssign(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "autoAssign");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getliststatus")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> getListStatus(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getListStatus");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/getsearchreport")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> getSearchReport(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getSearchReport");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/get-partner")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> getPartner(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getPartner");
		request.put("body", body);
		request.put("token", token);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/commentapp")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root','3p-service-digitex')")
	public ResponseEntity<?> commentV2(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "commentAppV2");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/quicklead")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> quickleadV2(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "quickLeadV2");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@RequestMapping("/v1/dataentry/uploaddigitex")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> uploadPartner(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("token", token);
		request.put("body", body);
		request.put("func", "uploadPartner");

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/get-token")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> getTokenSGBPO(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("body", body);
		request.put("token", token);
		request.put("func", "getTokenSaigonBpo");

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/uploadfile/{appId}/{partnerId}")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> uploadfile(@RequestHeader("Authorization") String token,
										@RequestPart("files")  MultipartFile[] files,
										@PathVariable String appId,
										@PathVariable String partnerId)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MultiValueMap<String, Object> parts_02 =
				new LinkedMultiValueMap<String, Object>();
		JsonNode body = null;
		request.put("func", "uploadFile");
		request.put("token", token);
		request.put("appId", appId);

		boolean validateIdCard = false;
		boolean validateHousehold = false;
		boolean validatePersonalImage = false;
		boolean validateACCA = false;
		boolean validateComment = false;

		Map partner = new HashMap();
		String partnerIdToGetPartner;
		if (appId.equals("new")) {
			partnerIdToGetPartner = partnerId;
			for (MultipartFile item : files) {
				if (item.getOriginalFilename().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())) {
					validatePersonalImage = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())) {
					validateACCA = true;
				}
			}
			if (validateIdCard && validateHousehold && validatePersonalImage && validateACCA) {
			} else {
				return ResponseEntity.status(200)
						.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
								"result_code", 3, "message", "Thieu document!"));
			}
		}else {
			for (MultipartFile item : files) {
				if (item.getOriginalFilename().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
					validateIdCard = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
					validateHousehold = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())) {
					validatePersonalImage = true;
				} else if (item.getOriginalFilename().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())) {
					validateACCA = true;
				}else {
					validateComment = true;
				}
			}
			if (!validateComment) {
			} else {
				if (validateIdCard || validateHousehold || validatePersonalImage || validateACCA){
					return ResponseEntity.status(200)
							.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
									"result_code", 3, "message", "Document khong hop le!"));
				}else {
					return ResponseEntity.status(200)
							.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
									"result_code", 3, "message", "Sai Document!"));
				}
			}

			Map<String, Object> requestGetApp = new HashMap<>();
			requestGetApp.put("func", "getByAppId");
			requestGetApp.put("token", token);
			Map<String, Object> bodyGetApp = new HashMap<>();
			Map<String, Object> appForBodyGetApp= new HashMap<>();
			appForBodyGetApp.put("applicationId", appId);
			bodyGetApp.put("data", appForBodyGetApp);
			requestGetApp.put("body", bodyGetApp);
			JsonNode responseGetApp = rabbitMQService.sendAndReceive("tpf-service-dataentry", requestGetApp);
			if(responseGetApp != null
					&& responseGetApp.path("data") != null
					&& responseGetApp.path("data").path("data") != null
					&& responseGetApp.path("data").path("data").get(0) != null
					&& responseGetApp.path("data").path("data").get(0).path("partnerId").isTextual()){
				partnerIdToGetPartner = responseGetApp.get("data").get("data").get(0).get("partnerId").asText();
			}else{
				return ResponseEntity.status(200)
						.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
								"result_code", 3, "message", "Partner not found!"));
			}
		}
		request.put("partnerId", partnerIdToGetPartner);
		try {
			Map<String, Object> partnerMap = new HashMap<>();
			partnerMap.put("partnerId", partnerIdToGetPartner);
			JsonNode partnerResponse = rabbitMQService.sendAndReceive("tpf-service-dataentry",
					Map.of("func", "getPartner","body", partnerMap));

			if(partnerResponse == null || partnerResponse.get("data").isNull()){
				return ResponseEntity.status(200)
						.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
								"result_code", 3, "message", "Partner not found!"));
			} else {
				partner = mapper.convertValue(partnerResponse.get("data").get(0), Map.class);
			}
			Object url = mapper.convertValue(partner, Map.class).get("url");

			ResponseEntity<?> res = new ResponseEntity<Authenticator.Success>(HttpStatus.CREATED);

			MultiValueMap<String, Object> parts =
					new LinkedMultiValueMap<String, Object>();
			for (MultipartFile item:
					files) {
				parts.add("file", item.getResource());
			}
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				HttpEntity<?> entity = new HttpEntity<>(parts, headers);
				res = restTemplate.postForEntity(urlUploadfile, entity, List.class);

				ObjectNode dataLog = mapper.createObjectNode();
				dataLog.put("type", "[==HTTP-LOG==]");
				dataLog.set("result", mapper.convertValue(res, JsonNode.class));
				log.info("{}", dataLog);
			}
			catch (Exception e) {
				ObjectNode dataLog = mapper.createObjectNode();
				dataLog.put("type", "[==HTTP-LOG==]");
				dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
				log.error("{}", dataLog);

				int i=0;
				do {
					Thread.sleep(30000);
					try{
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						HttpEntity<?> entity = new HttpEntity<>(parts, headers);
						res = restTemplate.postForEntity(urlUploadfile, entity, List.class);
						break;
					} catch (Exception ex) {
					}
					i = i +1;
				}while(i<2);
			}

			if (res.getStatusCodeValue() == 200){
				JsonNode outputDT = null;
				body = mapper.valueToTree(res.getBody());
				boolean checkIdCard = false;
				boolean checkHousehold = false;
				int i = 0;

				if (appId.equals("new")){
					ArrayNode documents = mapper.createArrayNode();
					if(files != null) {

						for (JsonNode item :body){
//							i = 0;
							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
								doc.put("file-name", "Personal-Image_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("Personal-Image_" + files[i].getOriginalFilename(),
										"Personal-Image_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("Personal-Image", multipartFileToSend.getResource());
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
								doc.put("file-name", "ACCA-Form_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("ACCA-Form_" + files[i].getOriginalFilename(),
										"ACCA-Form_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("ACCA-Form", multipartFileToSend.getResource());
							}
							i = i + 1;
						}
						i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())){
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())){
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}
						i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())){
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())){
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}
						parts_02.add("description", Map.of("files", documents));
					}
					try {
						if (parts_02.size() > 0) {
							String documentApi = (String) mapper.convertValue(url, Map.class).get("documentApi");

							HttpHeaders headers_DT = new HttpHeaders();
							if(partner.get("partnerId").equals("1")){
								headers_DT.set("authkey", partner.get("token").toString());

								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);

								ObjectNode dataLogReq = mapper.createObjectNode();
								dataLogReq.put("type", "[==HTTP-LOG-REQUEST==" + partner.get("partnerName") + "==]");
								dataLogReq.put("method", "POST");
								dataLogReq.put("url", documentApi);
								dataLogReq.put("data", entity_DT.toString());
								log.info("{}", dataLogReq);

								ResponseEntity<?> res_DT = restTemplate.postForEntity(documentApi, entity_DT, Object.class);

								Object map = mapper.valueToTree(res_DT.getBody());
								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));

								ObjectNode dataLog = mapper.createObjectNode();
								dataLog.put("type", "[==HTTP-LOG-RESPONSE==" + partner.get("partnerName") + "==]");
								dataLog.put("url", documentApi);
								dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
								log.info("{}", dataLog);
							} else if(partner.get("partnerId").equals("2")){
								Map<String, Object> tokenMap = new HashMap<>();
								Map<String, Object> tokenBodyMap = new HashMap<>();
								tokenBodyMap.put("partnerId", partner.get("partnerId"));
								tokenMap.put("body", tokenBodyMap);
								tokenMap.put("func", "getTokenSaigonBpo");
								JsonNode tokenResponse = rabbitMQService.sendAndReceive("tpf-service-dataentry", tokenMap);
								String tokenPartner = tokenResponse.path("data").asText();
								if(StringUtils.isEmpty(tokenPartner)){
									return ResponseEntity.status(200)
											.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
													"result_code", 3, "message", "Not get token saigon-bpo"));
								}

								headers_DT.setBearerAuth(tokenPartner);
								headers_DT.set("authkey", tokenPartner);
								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
								parts_02.remove("description");
								parts_02.add("Description", Map.of("files", documents));
								parts_02.set("access_token", tokenPartner);
								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);

								ObjectNode dataLogReq = mapper.createObjectNode();
								dataLogReq.put("type", "[==HTTP-LOG-REQUEST==" + partner.get("partnerName") + "==]");
								dataLogReq.put("method", "POST");
								dataLogReq.put("url", documentApi);
								dataLogReq.put("data", entity_DT.toString());
								log.info("{}", dataLogReq);

								ResponseEntity<?> res_DT = restTemplate.postForEntity(documentApi, entity_DT, Object.class);

								ObjectNode dataLog = mapper.createObjectNode();
								dataLog.put("type", "[==HTTP-LOG-RESPONSE==" + partner.get("partnerName") + "==]");
								dataLog.put("url", documentApi);
								dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
								log.info("{}", dataLog);

								Object map = mapper.valueToTree(res_DT.getBody());
								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));
							}
						}
					}
					catch (Exception e) {
						ObjectNode dataLog = mapper.createObjectNode();
						dataLog.put("type", "[==HTTP-LOG==]");
						dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
						log.error("{}", dataLog);
					}
				} else{
					Map<String, Object> request_docId = new HashMap<>();
					request_docId.put("func", "getDocumentId");
					request_docId.put("token", token);
					request_docId.put("appId", appId);
					JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request_docId);
					int countDocId = response.path("data").path("data").size();

					ArrayNode documents = mapper.createArrayNode();
					if(files != null) {
						for (JsonNode item :body){
							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
								doc.put("file-name", "Personal-Image_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								String docId = null;
								for (int j = 0; j < countDocId; j++){
									if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Customer Photograph.pdf".toUpperCase())){
										docId = response.path("data").path("data").get(j).path("urlid").textValue();
									}
								}
								doc.put("document-id", docId);
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("Personal-Image_" + files[i].getOriginalFilename(),
										"Personal-Image_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("Personal-Image", multipartFileToSend.getResource());
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
								doc.put("file-name", "ACCA-Form_" + item.path("originalname").textValue());
								doc.put("md5", item.path("md5").textValue());
								String docId = null;
								for (int j = 0; j < countDocId; j++){
									if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Application cum Credit Contract (ACCA).pdf".toUpperCase())){
										docId = response.path("data").path("data").get(j).path("urlid").textValue();
									}
								}
								doc.put("document-id", docId);
								documents.add(doc);

								MultipartFile multipartFileToSend = new MockMultipartFile("ACCA-Form_" + files[i].getOriginalFilename(),
										"ACCA-Form_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
								parts_02.add("ACCA-Form", multipartFileToSend.getResource());
							}
							i = i + 1;
						}

						i = 0;
						for (JsonNode item :body){

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())){
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_ID Card.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}
							}else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())){
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Family Book.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}

						i = 0;
						for (JsonNode item :body) {

							ObjectNode doc = mapper.createObjectNode();

							if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
								if (!checkIdCard) {
									doc.put("file-name", "ID-Card_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Notarization of ID card.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("ID-Card_" + files[i].getOriginalFilename(),
											"ID-Card_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("ID-Card", multipartFileToSend.getResource());

									checkIdCard = true;
								}

							} else if (item.path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
								if (!checkHousehold) {
									doc.put("file-name", "Household_" + item.path("originalname").textValue());
									doc.put("md5", item.path("md5").textValue());
									String docId = null;
									for (int j = 0; j < countDocId; j++) {
										if (response.path("data").path("data").get(j).path("originalname").textValue().toUpperCase().equals("TPF_Notarization of Family Book.pdf".toUpperCase())) {
											docId = response.path("data").path("data").get(j).path("urlid").textValue();
										}
									}
									doc.put("document-id", docId);
									documents.add(doc);

									MultipartFile multipartFileToSend = new MockMultipartFile("Household_" + files[i].getOriginalFilename(),
											"Household_" + files[i].getOriginalFilename(), files[i].getContentType(), files[i].getInputStream());
									parts_02.add("Household", multipartFileToSend.getResource());

									checkHousehold = true;
								}
							}
							i = i + 1;
						}
						parts_02.add("description", Map.of("files", documents));
					}
					try {
						if (parts_02.size() > 0) {
							String resumitDocumentApi = (String) mapper.convertValue(url, Map.class).get("resumitDocumentApi");
							HttpHeaders headers_DT = new HttpHeaders();

							if(partner.get("partnerId").equals("1")){
								headers_DT.set("authkey", partner.get("token").toString());
								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);

								ObjectNode dataLogReq = mapper.createObjectNode();
								dataLogReq.put("type", "[==HTTP-LOG-REQUEST==" + partner.get("partnerName") + "==]");
								dataLogReq.put("method", "POST");
								dataLogReq.put("appId", appId);
								dataLogReq.put("url", resumitDocumentApi);
								dataLogReq.put("data", entity_DT.toString());
								log.info("{}", dataLogReq);

								ResponseEntity<?> res_DT = restTemplate.postForEntity(resumitDocumentApi, entity_DT, Object.class);

								ObjectNode dataLog = mapper.createObjectNode();
								dataLog.put("type", "[==HTTP-LOG-RESPONSE==" + partner.get("partnerName") + "==]");
								dataLog.put("url", resumitDocumentApi);
								dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
								log.info("{}", dataLog);

								Object map = mapper.valueToTree(res_DT.getBody());
								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));

							} else if(partner.get("partnerId").equals("2")){
								Map<String, Object> tokenMap = new HashMap<>();
                                Map<String, Object> tokenBodyMap = new HashMap<>();
                                tokenBodyMap.put("partnerId", partner.get("partnerId"));
                                tokenMap.put("token", token);
								tokenMap.put("func", "getTokenSaigonBpo");
                                tokenMap.put("body", tokenBodyMap);
								JsonNode tokenResponse = rabbitMQService.sendAndReceive("tpf-service-dataentry", tokenMap);
								String tokenPartner = tokenResponse.path("data").asText();
								if(StringUtils.isEmpty(tokenPartner)){
									return ResponseEntity.status(200)
											.header("x-pagination-total", "0").body(Map.of("reference_id", UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
													"result_code", 3, "message", "Not get token saigon-bpo"));
								}
								headers_DT.setBearerAuth(tokenPartner);
								headers_DT.set("authkey", tokenPartner);
								headers_DT.setContentType(MediaType.MULTIPART_FORM_DATA);
								parts_02.remove("description");
								parts_02.add("Description", Map.of("files", documents));
								parts_02.set("access_token", tokenPartner);
								HttpEntity<?> entity_DT = new HttpEntity<>(parts_02, headers_DT);

								ObjectNode dataLogReq = mapper.createObjectNode();
								dataLogReq.put("type", "[==HTTP-LOG-REQUEST==" + partner.get("partnerName") + "==]");
								dataLogReq.put("method", "POST");
								dataLogReq.put("appId", appId);
								dataLogReq.put("url", resumitDocumentApi);
								dataLogReq.put("data", entity_DT.toString());
								log.info("{}", dataLogReq);

								ResponseEntity<?> res_DT = restTemplate.postForEntity(resumitDocumentApi, entity_DT, Object.class);

								ObjectNode dataLog = mapper.createObjectNode();
								dataLog.put("type", "[==HTTP-LOG-RESPONSE==" + partner.get("partnerName") + "==]");
								dataLog.put("url", resumitDocumentApi);
								dataLog.set("result", mapper.convertValue(res_DT, JsonNode.class));
								log.info("{}", dataLog);

								Object map = mapper.valueToTree(res_DT.getBody());
								outputDT = mapper.readTree(mapper.writeValueAsString(((JsonNode) map).get("output")));
							}
						}
					}
					catch (Exception e) {
						ObjectNode dataLog = mapper.createObjectNode();
						dataLog.put("type", "[==HTTP-LOG==]");
						dataLog.set("result", mapper.convertValue(e.toString(), JsonNode.class));
						log.error("{}", dataLog);
					}
				}
				if (outputDT == null){
					JsonNode jNode = mergeFile(body, null);
					request.put("body", jNode);
					request.put("uploadDigiTex", "FAIL");

				}else {
					JsonNode jNode = mergeFile(body, mapper.valueToTree(outputDT));
					request.put("body", jNode);
				}
			}else{
				return ResponseEntity.status(200)
						.header("x-pagination-total", "0").body(Map.of("reference_id",UUID.randomUUID().toString(), "date_time", new Timestamp(new Date().getTime()),
								"result_code", 1, "message", "uploadFile TPF fail!"));
			}

		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(500)
					.header("x-pagination-total", "0").body(e.toString());
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.header("x-pagination-total", "0").body(e.toString());
		}

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-dataentry", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/deResponseQuery")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> deResponseQuery(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "deResponseQuery");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/v1/dataentry/deSaleQueue")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> deSaleQueue(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "deSaleQueue");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	//Field Verification - Initiate Verification
	@PostMapping("/v1/dataentry/initiateVerification")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> initiateVerification(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "initiateVerification");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	//Field Verification - Waive Off All
	@PostMapping("/v1/dataentry/waiveOffAll")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> waiveOffAll(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "waiveOffAll");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	//Field Verification - Field Investigation
	@PostMapping("/v1/dataentry/fieldInvestigation")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-dataentry','tpf-service-root')")
	public ResponseEntity<?> fieldInvestigation(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "fieldInvestigation");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-automation", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}
}

