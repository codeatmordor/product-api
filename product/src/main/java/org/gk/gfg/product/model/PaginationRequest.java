
// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.model;

/**
 * @author Gaurav_Singh3
 *
 */
public class PaginationRequest {

  private int limit;
  private int offset;
  private boolean includeCount;

  /**
   * @return the limit
   */
  public int getLimit() {
    return limit;
  }

  /**
   * @param limit the limit to set
   */
  public void setLimit(final int limit) {
    this.limit = limit;
  }

  /**
   * 
   */
  public PaginationRequest() {

  }

  /**
   * @param limit
   * @param offset
   */
  public PaginationRequest(final int limit, final int offset, final Boolean includeCount) {
    super();
    this.limit = limit;
    this.offset = offset;
    this.includeCount = includeCount;
  }

  public PaginationRequest(final int limit, final int offset) {
    super();
    this.limit = limit;
    this.offset = offset;
    this.includeCount = false;
  }

  /**
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @param offset the offset to set
   */
  public void setOffset(final int offset) {
    this.offset = offset;
  }

  /**
   * @return the includeCount
   */
  public boolean isIncludeCount() {
    return includeCount;
  }

  /**
   * @param includeCount the includeCount to set
   */
  public void setIncludeCount(final boolean includeCount) {
    this.includeCount = includeCount;
  }
}
