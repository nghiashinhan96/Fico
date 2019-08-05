package vn.com.tpf.microservices.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import vn.com.tpf.microservices.services.RabbitMQService;

@RestController
public class BaseController {

  @Autowired
  private RabbitMQService rabbitMQService;

  @GetMapping("/")
  public ResponseEntity<?> hello(@RequestParam Map<String, String> param) {
    return ResponseEntity.ok().body(param);
  }

  @PostMapping("/v1/login")
  public ResponseEntity<?> login(@RequestBody JsonNode body) throws Exception {
    Map<String, Object> request = new HashMap<>();
    request.put("func", "login");
    request.put("body", body);

    JsonNode response = rabbitMQService.sendAndReceive("service_authorization", request);
    return ResponseEntity.status(response.get("status").asInt()).body(response.get("data"));
  }

  @GetMapping("/v1/logout")
  @PreAuthorize("#oauth2.hasAnyScope('authentication_trust','authentication_write')")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) throws Exception {
    Map<String, Object> request = new HashMap<>();
    request.put("func", "logout");
    request.put("token", token);

    JsonNode response = rabbitMQService.sendAndReceive("service_authentication", request);
    return ResponseEntity.status(response.get("status").asInt()).body(response.get("data"));
  }

  @PostMapping("/v1/change-password")
  @PreAuthorize("#oauth2.hasAnyScope('authentication_trust','authentication_write')")
  public ResponseEntity<?> create(@RequestHeader("Authorization") String token, @RequestBody JsonNode body)
      throws Exception {
    Map<String, Object> request = new HashMap<>();
    request.put("func", "changePassword");
    request.put("token", token);
    request.put("body", body);

    JsonNode response = rabbitMQService.sendAndReceive("service_authentication", request);
    return ResponseEntity.status(response.get("status").asInt()).body(response.get("data"));
  }

  @GetMapping("/v1/me")
  @PreAuthorize("#oauth2.hasAnyScope('authorization_trust','authorization_write','authorization_read')")
  public ResponseEntity<?> me(@RequestHeader("Authorization") String token) throws Exception {
    Map<String, Object> request = new HashMap<>();
    request.put("func", "getInfoAccount");
    request.put("token", token);

    JsonNode response = rabbitMQService.sendAndReceive("service_authorization", request);
    return ResponseEntity.status(response.get("status").asInt()).body(response.get("data"));
  }

}