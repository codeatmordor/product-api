package org.gk.gfg.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductGatewayException extends RuntimeException {
  private static final long serialVersionUID = -2481396528846407142L;

  public ProductGatewayException(String exception) {
    super(exception);
  }
}

