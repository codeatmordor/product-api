package org.gk.gfg.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductServiceException extends RuntimeException {
  private static final long serialVersionUID = -2481396528846407142L;

  public ProductServiceException(String exception) {
    super(exception);
  }
}
