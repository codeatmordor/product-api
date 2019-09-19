

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.controller;

import java.util.List;
import javax.validation.Valid;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/gfg/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {
  @Value("${bulk.request.limit:1000}")
  private Integer limit;

  private static final String APPLICATION_JSON = "application/json";

  @Autowired
  private ProductService productService;

  @PostMapping(path = "/products", consumes = APPLICATION_JSON)
  public ResponseEntity<List<Product>> create(@Valid @RequestBody final List<Product> products) {
    return new ResponseEntity<>(productService.create(products), HttpStatus.CREATED);
  }


}

