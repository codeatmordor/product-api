package org.gk.gfg.product.controller;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.gk.gfg.product.model.PaginationRequest;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.model.ResponseWrapper;
import org.gk.gfg.product.model.SearchProductDto;
import org.gk.gfg.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PutMapping(path = "/products/{productId}", produces = APPLICATION_JSON)
  public ResponseEntity<Product> update(@PathVariable("productId") final String productId,
      @Valid @RequestBody final Product product) {
    return new ResponseEntity<>(productService.update(product, productId), HttpStatus.ACCEPTED);
  }

  @GetMapping(path = "/products/{productId}", produces = APPLICATION_JSON)
  public ResponseEntity<Product> get(@PathVariable("productId") final String productId) {
    return new ResponseEntity<>(productService.get(productId), HttpStatus.OK);
  }

  @DeleteMapping(path = "/products/{productId}")
  public ResponseEntity<Integer> delete(@PathVariable("productId") final String productId) {
    productService.delete(productId);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PutMapping(path = "/products/bulk", produces = APPLICATION_JSON)
  public ResponseEntity<Set<Product>> update(@Valid @RequestBody final Set<Product> products) {
    return new ResponseEntity<>(productService.update(products), HttpStatus.ACCEPTED);
  }

  @GetMapping(produces = APPLICATION_JSON)
  public ResponseEntity<ResponseWrapper<Product>> search(
      @RequestParam(name = "where", required = false) final String where,
      @RequestParam(name = "limit", required = false, defaultValue = "1000") final int limit,
      @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset,
      @RequestParam(name = "sortby", required = false) final String sortBy,
      @RequestParam(name = "sortorder", required = false) final String sortOrder,
      @RequestParam(name = "include_count", required = false,
          defaultValue = "false") final Boolean include_count) {
    final SearchProductDto searchDto = new SearchProductDto(where, sortBy, sortOrder);
    final PaginationRequest pageReq = new PaginationRequest(limit, offset, include_count);
    final ResponseWrapper<Product> result = productService.findProduct(searchDto, pageReq);
    if (result.getResult().size() == 0) {
      return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(result, HttpStatus.OK);
    }
  }


}

