package org.gk.gfg.product.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper<T> implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -3831793168206741231L;
  private Long count;
  private List<T> result;

  /**
   * @return the count
   */
  public Long getCount() {
    return count;
  }

  /**
   * @param l the count to set
   */
  public void setCount(final Long l) {
    this.count = l;
  }

  /**
   * @return the result
   */
  public List<T> getResult() {
    return result;
  }

  /**
   * @param count
   * @param result
   */
  public ResponseWrapper(final long count, final List<T> result) {
    super();
    this.count = count;
    this.result = result;
  }

  /**
   *
   */
  public ResponseWrapper() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param result the result to set
   */
  public void setResult(final List<T> result) {
    this.result = result;
  }

}
