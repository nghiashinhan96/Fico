package vn.com.tpf.microservices.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.tpf.microservices.services.RabbitMQService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AutoAllocationController {

	@Autowired
	private RabbitMQService rabbitMQService;

	@PostMapping("/tpf-service-autoallocation/etl-push-data")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> pushDataFromF1(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "ETLPushData");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation1", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/tpf-service-autoallocation/upload-user")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> uploadUser(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "uploadUser");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation1", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@PostMapping("/tpf-service-autoallocation/add-user")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> addUser(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "addUser");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation1", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@GetMapping("/tpf-service-autoallocation/get-all-user")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> getAllUser(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getAllUser");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation1", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@GetMapping("/tpf-service-autoallocation/get-user")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
			throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "getUser");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation1", request);
		return ResponseEntity.status(response.path("status").asInt(500))
				.header("x-pagination-total", response.path("total").asText("0")).body(response.path("data"));
	}

	@GetMapping("/tpf-service-autoallocation/get-assign-config/")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> setAssignConfig(@RequestHeader("Authorization") String token) throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "assignConfig");
		request.put("token", token);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation", request);
		return ResponseEntity.status(response.path("status").asInt(500)).body(response.path("data"));
	}


	@PostMapping("/tpf-service-autoallocation/set-assign-config/")
	@PreAuthorize("#oauth2.hasAnyScope('tpf-service-root')")
	public ResponseEntity<?> getAssignConfig(@RequestHeader("Authorization") String token, @RequestBody JsonNode body) throws Exception {
		Map<String, Object> request = new HashMap<>();
		request.put("func", "setAssignConfig");
		request.put("token", token);
		request.put("body", body);

		JsonNode response = rabbitMQService.sendAndReceive("tpf-service-autoallocation", request);
		return ResponseEntity.status(response.path("status").asInt(500)).body(response.path("data"));
	}


}