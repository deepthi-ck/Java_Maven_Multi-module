package com.example.order.repository;

import com.example.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {
  private final Map<Long, Order> store = new ConcurrentHashMap<Long, Order>();
  private final AtomicLong seq = new AtomicLong(1);

  public Order save(Order order) {
    Long id = order.getId() == null ? seq.getAndIncrement() : order.getId();
    Order saved = new Order(id, order.getCustomerRef(), order.getLines(), order.getTotal());
    store.put(id, saved);
    return saved;
  }

  public Optional<Order> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  public List<Order> findAll() {
    return new ArrayList<Order>(store.values());
  }
}