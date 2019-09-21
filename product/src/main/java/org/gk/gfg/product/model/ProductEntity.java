package org.gk.gfg.product.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product")
public class ProductEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String product_id;

  @Column(name = "title")
  private String title;

  @Column(name = "brand")
  private String brand;

  @Column(name = "description")
  private String description;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created = new Date();


  @Temporal(TemporalType.TIMESTAMP)
  private Date modified = new Date();

  /**
   * @param product_id
   * @param title
   * @param brand
   * @param description
   * @param price
   * @param color
   */
  public ProductEntity(String product_id, String title, String brand, String description, int price,
      String color) {
    super();
    this.product_id = product_id;
    this.title = title;
    this.brand = brand;
    this.description = description;
    this.price = price;
    this.color = color;
  }


  public ProductEntity(Product product) {
    super();
    this.product_id = product.getProductId();
    this.title = product.getTitle();
    this.brand = product.getBrand();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.color = product.getColor();
    this.created = product.getCreated();
    this.modified = product.getModified();
  }

  /**
   *
   */
  public ProductEntity() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Column(name = "price")
  private int price;

  @Column(name = "color")
  private String color;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((brand == null) ? 0 : brand.hashCode());
    result = prime * result + ((color == null) ? 0 : color.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + price;
    result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProductEntity other = (ProductEntity) obj;
    if (brand == null) {
      if (other.brand != null)
        return false;
    } else if (!brand.equals(other.brand))
      return false;
    if (color == null) {
      if (other.color != null)
        return false;
    } else if (!color.equals(other.color))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (price != other.price)
      return false;
    if (product_id == null) {
      if (other.product_id != null)
        return false;
    } else if (!product_id.equals(other.product_id))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ProductEntity [product_id=" + product_id + ", title=" + title + ", brand=" + brand
        + ", description=" + description + ", price=" + price + ", color=" + color + "]";
  }

  /**
   * @return the product_id
   */
  public String getProduct_id() {
    return product_id;
  }

  /**
   * @param product_id the product_id to set
   */
  public void setProduct_id(String product_id) {
    this.product_id = product_id;
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
   * @return the price
   */
  public int getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * @return the color
   */
  public String getColor() {
    return color;
  }

  /**
   * @param color the color to set
   */
  public void setColor(String color) {
    this.color = color;
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

