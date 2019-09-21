package org.gk.gfg.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchProductDto {

  private String sortBy;

  private String sortOrder;

  private String where;

  public SearchProductDto() {

  }

  public SearchProductDto(final String where, final String sortBy, final String sortOrder) {
    this.where = where;
    this.sortBy = sortBy;
    this.sortOrder = sortOrder;

  }

  /**
   * @return the sortBy
   */
  public String getSortBy() {
    return sortBy;
  }

  /**
   * @param sortBy the sortBy to set
   */
  public void setSortBy(final String sortBy) {
    this.sortBy = sortBy;
  }

  /**
   * @return the where
   */
  public String getWhere() {
    return where;
  }

  /**
   * @param where the where to set
   */
  public void setWhere(final String where) {
    this.where = where;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(final String sortOrder) {
    this.sortOrder = sortOrder;
  }
}
