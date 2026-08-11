package com.example.product.model;

import java.math.BigDecimal;

public class Product {
  private final Long id;
  private final String sku;
  private final String name;
  private final BigDecimal price;
  private final int stock;

  public Product(Long id, String sku, String name, BigDecimal price, int stock) {
    this.id = id;
    this.sku = sku;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public Long getId() {
    return id;
  }

  public String getSku() {
    return sku;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }
}