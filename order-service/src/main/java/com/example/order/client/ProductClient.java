package com.example.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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
    @SuppressWarnings("unchecked")
    Map<String, Object> body = restTemplate.getForObject(baseUrl + "/api/products/{id}", Map.class, productId);
    if (body == null || body.get("price") == null) {
      throw new IllegalStateException("product price missing for id=" + productId);
    }
    return new BigDecimal(body.get("price").toString());
  }
}