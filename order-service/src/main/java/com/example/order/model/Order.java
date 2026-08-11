package com.example.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
  private final Long id;
  private final String customerRef;
  private final List<OrderLine> lines;
  private final BigDecimal total;

  public Order(Long id, String customerRef, List<OrderLine> lines, BigDecimal total) {
    this.id = id;
    this.customerRef = customerRef;
    this.lines = new ArrayList<OrderLine>(lines);
    this.total = total;
  }

  public Long getId() {
    return id;
  }

  public String getCustomerRef() {
    return customerRef;
  }

  public List<OrderLine> getLines() {
    return Collections.unmodifiableList(lines);
  }

  public BigDecimal getTotal() {
    return total;
  }
}