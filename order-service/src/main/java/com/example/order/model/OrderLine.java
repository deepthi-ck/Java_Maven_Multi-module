package com.example.order.model;

import java.math.BigDecimal;

public class OrderLine {
  private final Long productId;
  private final int quantity;
  private final BigDecimal unitPrice;

  public OrderLine(Long productId, int quantity, BigDecimal unitPrice) {
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public Long getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
}