package com.example.common.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {
  private final BigDecimal amount;

  public Money(BigDecimal amount) {
    if (amount == null) {
      throw new IllegalArgumentException("amount required");
    }
    this.amount = amount.setScale(2, RoundingMode.HALF_UP);
  }

  public static Money of(String value) {
    return new Money(new BigDecimal(value));
  }

  public Money plus(Money other) {
    return new Money(this.amount.add(other.amount));
  }

  public Money times(int qty) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(qty)));
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Money)) {
      return false;
    }
    Money money = (Money) o;
    return Objects.equals(amount, money.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount);
  }

  @Override
  public String toString() {
    return amount.toPlainString();
  }
}