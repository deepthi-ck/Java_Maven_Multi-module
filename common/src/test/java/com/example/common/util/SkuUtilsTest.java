package com.example.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkuUtilsTest {
  @Test
  void normalizeUppercases() {
    assertEquals("ABC-1", SkuUtils.normalize(" abc-1 "));
  }

  @Test
  void validateLength() {
    assertTrue(SkuUtils.isValid("SKU1"));
    assertFalse(SkuUtils.isValid("AB"));
  }

  @Test
  void normalizeRejectsBlank() {
    assertThrows(IllegalArgumentException.class, () -> SkuUtils.normalize("  "));
  }
}