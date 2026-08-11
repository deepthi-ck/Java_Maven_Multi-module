package com.example.common.util;

public final class SkuUtils {
  private SkuUtils() {
  }

  public static String normalize(String sku) {
    if (sku == null || sku.trim().isEmpty()) {
      throw new IllegalArgumentException("sku required");
    }
    return sku.trim().toUpperCase();
  }

  public static boolean isValid(String sku) {
    if (sku == null) {
      return false;
    }
    String n = sku.trim();
    return n.length() >= 3 && n.length() <= 32;
  }
}