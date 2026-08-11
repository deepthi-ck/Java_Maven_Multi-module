package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@Valid @RequestBody OrderRequest request) {
    return orderService.create(request);
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable Long id) {
    return orderService.get(id);
  }

  @GetMapping
  public List<Order> list() {
    return orderService.list();
  }
}