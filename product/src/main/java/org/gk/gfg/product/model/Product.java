

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.model;

import java.util.Date;
import org.gk.gfg.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
  private String productId;
  private String title;
  private String description;
  private ProductColor color;
  private Integer price;
  private String brand;
  private Date created;
  private Date modified;

  public Product() {
    super();
    // TODO Auto-generated constructor stub
  }

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
    this.created = product.getCreated();
    this.modified = product.getModified();
  }



  /**
   * @return the productId
   */
  public String getProductId() {
    return productId;
  }

  /**
   * @param productId the productId to set
   */
  public void setProductId(String productId) {
    this.productId = productId;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the color
   */
  public ProductColor getColor() {
    return color;
  }

  /**
   * @param color the color to set
   */
  public void setColor(ProductColor color) {
    this.color = color;
  }

  /**
   * @return the price
   */
  public Integer getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * @param brand the brand to set
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * @return the created
   */
  public Date getCreated() {
    return created;
  }

  /**
   * @param created the created to set
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * @return the modified
   */
  public Date getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(Date modified) {
    this.modified = modified;
  }

}

