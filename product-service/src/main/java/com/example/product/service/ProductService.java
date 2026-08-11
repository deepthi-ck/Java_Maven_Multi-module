package com.example.product.service;

import com.example.common.model.Money;
import com.example.common.util.SkuUtils;
import com.example.product.dto.ProductRequest;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public Product create(ProductRequest request) {
    String sku = SkuUtils.normalize(request.getSku());
    Money price = new Money(request.getPrice());
    Product product = new Product(null, sku, request.getName().trim(), price.getAmount(), request.getStock());
    return repository.save(product);
  }

  public Product get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NoSuchElementException("product " + id));
  }

  public List<Product> list() {
    return repository.findAll();
  }
}