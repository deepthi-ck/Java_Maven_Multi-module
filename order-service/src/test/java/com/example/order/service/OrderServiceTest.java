package com.example.order.service;

import com.example.order.client.ProductClient;
import com.example.order.dto.OrderLineRequest;
import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {
  @Test
  void createComputesTotal() {
    ProductClient client = mock(ProductClient.class);
    when(client.fetchUnitPrice(1L)).thenReturn(new BigDecimal("10.00"));
    OrderService service = new OrderService(new OrderRepository(), client);

    OrderLineRequest line = new OrderLineRequest();
    line.setProductId(1L);
    line.setQuantity(3);
    OrderRequest req = new OrderRequest();
    req.setCustomerRef("cust-1");
    req.setLines(Collections.singletonList(line));

    Order saved = service.create(req);
    assertNotNull(saved.getId());
    assertEquals(new BigDecimal("30.00"), saved.getTotal());
  }
}