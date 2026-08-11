package com.example.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class OrderRequest {
  @NotBlank
  private String customerRef;
  @NotEmpty
  @Valid
  private List<OrderLineRequest> lines;

  public String getCustomerRef() {
    return customerRef;
  }

  public void setCustomerRef(String customerRef) {
    this.customerRef = customerRef;
  }

  public List<OrderLineRequest> getLines() {
    return lines;
  }

  public void setLines(List<OrderLineRequest> lines) {
    this.lines = lines;
  }
}