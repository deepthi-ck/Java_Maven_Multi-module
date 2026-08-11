package com.example.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {
  @Test
  void plusAndTimes() {
    Money total = Money.of("10.00").plus(Money.of("2.50")).times(2);
    assertEquals(Money.of("25.00"), total);
  }

  @Test
  void rejectsNull() {
    assertThrows(IllegalArgumentException.class, () -> new Money(null));
  }
}