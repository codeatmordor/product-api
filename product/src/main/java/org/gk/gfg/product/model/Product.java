

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String productId;
  private String title;
  private String description;
  private String color;
  private int price;
  private String brand;

  /**
   * @param productId
   * @param title
   * @param description
   * @param color
   * @param price
   * @param brand
   */
  public Product(ProductEntity product) {
    super();
    this.productId = product.getProduct_id();
    this.title = product.getTitle();
    this.description = product.getDescription();
    this.color = product.getColor();
    this.price = product.getPrice();
    this.brand = product.getBrand();
  }

}

