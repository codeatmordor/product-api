
package org.gk.gfg.product.model;

public enum ProductColor {
  BLUE, GREEN, WHITE, BLACK, ORANGE, RED, YELLOW;

  public static boolean has(String str) {
    for (ProductColor c : ProductColor.values()) {
      if (c.name().equals(str))
        return true;
    }
    return false;
  }
}

