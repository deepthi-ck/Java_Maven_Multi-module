package com.example.order.controller;

import com.example.order.client.ProductClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Same-origin product facade for the order-service console UI.
 * Delegates to product-service through the existing RestTemplate client.
 */
@RestController
@RequestMapping("/api/products")
public class ProductGatewayController {
  private final ProductClient productClient;

  public ProductGatewayController(ProductClient productClient) {
    this.productClient = productClient;
  }

  @GetMapping
  public List<Map<String, Object>> list() {
    return productClient.list();
  }

  @GetMapping("/{id}")
  public Map<String, Object> get(@PathVariable Long id) {
    return productClient.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Object> create(@RequestBody Map<String, Object> request) {
    return productClient.create(request);
  }
}
