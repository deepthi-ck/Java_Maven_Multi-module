package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
  private final Map<Long, Product> store = new ConcurrentHashMap<Long, Product>();
  private final AtomicLong seq = new AtomicLong(1);

  public Product save(Product product) {
    Long id = product.getId() == null ? seq.getAndIncrement() : product.getId();
    Product saved = new Product(id, product.getSku(), product.getName(), product.getPrice(), product.getStock());
    store.put(id, saved);
    return saved;
  }

  public Optional<Product> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  public List<Product> findAll() {
    return new ArrayList<Product>(store.values());
  }
}