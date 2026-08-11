package com.example.product.service;

import com.example.product.dto.ProductRequest;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductServiceTest {
  @Test
  void createNormalizesSku() {
    ProductService service = new ProductService(new ProductRepository());
    ProductRequest req = new ProductRequest();
    req.setSku(" abc-9 ");
    req.setName("Widget");
    req.setPrice(new BigDecimal("12.5"));
    req.setStock(3);
    Product saved = service.create(req);
    assertNotNull(saved.getId());
    assertEquals("ABC-9", saved.getSku());
    assertEquals(new BigDecimal("12.50"), saved.getPrice());
  }
}