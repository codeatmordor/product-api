package org.gk.gfg.product.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

  private static final long serialVersionUID = -3698285747559476184L;
  private final String jwtToken;

  public JwtResponse(String jwttoken) {
    this.jwtToken = jwttoken;
  }

  public String getToken() {
    return this.jwtToken;
  }
}

