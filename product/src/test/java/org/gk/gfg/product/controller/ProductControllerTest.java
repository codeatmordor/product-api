package org.gk.gfg.product.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import org.gk.gfg.product.exception.ProductGatewayException;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class ProductControllerTest {

  @MockBean
  private ProductService productService;

  @Autowired
  private ProductController productController;

  @TestConfiguration
  static class ProductControllerTestConfiguration {
    @Bean
    public ProductController productController() {
      return new ProductController();
    }
  }

  @Before
  public void setUp() throws SQLException {
    Mockito.when(productService.create(Matchers.any())).thenReturn(new ArrayList<Product>());
  }

  @Test(expected = ProductGatewayException.class)
  public void createProductThrowsException() {
    productController.create(new ArrayList<Product>());
  }

  @Test(expected = ProductGatewayException.class)
  public void updateProductThrowsException() {
    productController.update(new HashSet<Product>());
  }
}
