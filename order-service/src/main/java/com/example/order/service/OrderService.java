package com.example.order.service;

import com.example.common.model.Money;
import com.example.order.client.ProductClient;
import com.example.order.dto.OrderLineRequest;
import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.model.OrderLine;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
  private final OrderRepository repository;
  private final ProductClient productClient;

  public OrderService(OrderRepository repository, ProductClient productClient) {
    this.repository = repository;
    this.productClient = productClient;
  }

  public Order create(OrderRequest request) {
    List<OrderLine> lines = new ArrayList<OrderLine>();
    Money total = Money.of("0.00");
    for (OrderLineRequest lineReq : request.getLines()) {
      Money unit = new Money(productClient.fetchUnitPrice(lineReq.getProductId()));
      lines.add(new OrderLine(lineReq.getProductId(), lineReq.getQuantity(), unit.getAmount()));
      total = total.plus(unit.times(lineReq.getQuantity()));
    }
    Order order = new Order(null, request.getCustomerRef().trim(), lines, total.getAmount());
    return repository.save(order);
  }

  public Order get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NoSuchElementException("order " + id));
  }

  public List<Order> list() {
    return repository.findAll();
  }
}