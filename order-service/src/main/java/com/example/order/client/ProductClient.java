package com.example.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ProductClient {
  private final RestTemplate restTemplate;
  private final String baseUrl;

  public ProductClient(RestTemplate restTemplate,
                       @Value("${product.service.base-url:http://localhost:8082}") String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  public BigDecimal fetchUnitPrice(Long productId) {
    Map<String, Object> body = getById(productId);
    if (body == null || body.get("price") == null) {
      throw new IllegalStateException("product price missing for id=" + productId);
    }
    return new BigDecimal(body.get("price").toString());
  }

  public List<Map<String, Object>> list() {
    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
        baseUrl + "/api/products",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Map<String, Object>>>() { });
    List<Map<String, Object>> body = response.getBody();
    return body == null ? Collections.<Map<String, Object>>emptyList() : body;
  }

  public Map<String, Object> getById(Long productId) {
    @SuppressWarnings("unchecked")
    Map<String, Object> body = restTemplate.getForObject(baseUrl + "/api/products/{id}", Map.class, productId);
    return body;
  }

  public Map<String, Object> create(Map<String, Object> request) {
    @SuppressWarnings("unchecked")
    Map<String, Object> body = restTemplate.postForObject(
        baseUrl + "/api/products",
        new HttpEntity<Map<String, Object>>(request),
        Map.class);
    return body;
  }
}
