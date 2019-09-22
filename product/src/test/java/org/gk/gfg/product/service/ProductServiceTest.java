package org.gk.gfg.product.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.gk.gfg.product.entity.ProductEntity;
import org.gk.gfg.product.exception.ProductServiceException;
import org.gk.gfg.product.model.PaginationRequest;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.model.ProductColor;
import org.gk.gfg.product.model.ResponseWrapper;
import org.gk.gfg.product.model.SearchProductDto;
import org.gk.gfg.product.repository.ProductRepository;
import org.gk.gfg.product.search.SpecificationSearchCriteriaCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

  private static final String INVALID_PRODUCT_ID = "INVALID-PRODUCT_ID";
  private static final String VALID_PRODUCT_ID = "PRODUCT-UUID-99-##";

  @TestConfiguration
  static class ProductServiceTestConfiguration {
    @Bean
    public ProductService productService() {
      return new ProductService();
    }
  }

  @MockBean
  private ProductRepository productRepo;
  @Autowired
  private ProductService productService;

  @Autowired
  private SpecificationSearchCriteriaCreator specificationSearchCriteriaCreator;
  @MockBean
  private SpecificationSearchCriteriaCreator specSearchCreator;

  @Before
  public void setUp() throws SQLException {
    Mockito.when(productRepo.findById(Matchers.anyString())).thenReturn(getProductEntity2());
    Mockito.when(productRepo.saveAll(Matchers.anyObject())).thenReturn(getProductEntity());
  }



  @Test()
  public void createProductSucceeds() {
    List<Product> products = new ArrayList<>();
    products.add(getProduct().get());
    List<Product> savedProduct = productService.create(products);
    Assert.assertEquals(savedProduct.get(0).getProductId(), products.get(0).getProductId());
  }

  @Test(expected = ProductServiceException.class)
  public void createProductThrowsExceptionOnEmptyList() {
    List<Product> products = new ArrayList<>();
    productService.create(products);
  }

  @Test
  public void getProductSucceeds() {
    Product p = productService.get(VALID_PRODUCT_ID);
    Assert.assertEquals(p.getProductId(), VALID_PRODUCT_ID);
  }

  @Test(expected = ProductServiceException.class)
  public void getProductThrowsExceptionOnEmptyId() {
    String productId = "";
    Product p = productService.get(productId);
  }

  @Test(expected = ProductServiceException.class)
  public void getProductThrowsExceptionOnInvalidId() {
    Mockito.when(productRepo.findById(INVALID_PRODUCT_ID)).thenReturn(Optional.empty());
    Product p = productService.get(INVALID_PRODUCT_ID);
  }

  @Test
  public void updateProductSucceeds() {
    Mockito.when(productRepo.findById(VALID_PRODUCT_ID)).thenReturn(getProductEntity2());
    Mockito.when(productRepo.save(Matchers.anyObject())).thenReturn(getProductEntity2().get());
    Product p = getProduct().get();
    String productId = VALID_PRODUCT_ID;
    Product updatedProduct = productService.update(p, productId);
    Assert.assertEquals(p.getProductId(), updatedProduct.getProductId());
  }

  @Test(expected = ProductServiceException.class)
  public void updateProductThrowsExceptionOnEmptyId() {
    Mockito.when(productRepo.findById(VALID_PRODUCT_ID)).thenReturn(getProductEntity2());
    Mockito.when(productRepo.save(Matchers.anyObject())).thenReturn(getProductEntity2().get());
    Product p = getProduct().get();
    String productId = "";
    productService.update(p, productId);
  }

  @Test(expected = ProductServiceException.class)
  public void updateProductThrowsExceptionOnInvalidId() {
    Mockito.when(productRepo.findById(INVALID_PRODUCT_ID)).thenReturn(Optional.empty());
    Mockito.when(productRepo.save(Matchers.anyObject())).thenReturn(getProductEntity2().get());
    Product p = getProduct().get();
    String productId = INVALID_PRODUCT_ID;
    productService.update(p, productId);
  }

  @Test(expected = ProductServiceException.class)
  public void updateProductThrowsExceptionOnEmptyList() {
    Set<Product> products = new HashSet<>();
    productService.update(products);
  }

  @Test
  public void searchProductSucceeds() {
    SearchProductDto sd = new SearchProductDto("title=Samsung M30", null, null);
    final PaginationRequest pageReq = new PaginationRequest(1000, 0, false);
    Mockito
        .when(productRepo.findAll(Matchers.any(Specification.class), Matchers.any(Pageable.class)))
        .thenReturn(getPage());
    ResponseWrapper<Product> res = productService.findProduct(sd, pageReq);
    Assert.assertEquals(res.getResult().get(0).getTitle(), "Samsung M30");
  }


  private Page<ProductEntity> getPage() {
    List<ProductEntity> expected = new ArrayList<>();
    expected.add(getProductEntity2().get());
    Page<ProductEntity> foundPage = new PageImpl<>(expected);
    return foundPage;
  }

  private List<ProductEntity> getProductEntity() {
    ProductEntity e = new ProductEntity();
    e.setBrand("SAMSUNG");
    e.setColor(ProductColor.BLACK);
    e.setPrice(10000);
    e.setDescription("Samsung M30 Black");
    e.setTitle("Samsung M30");
    e.setProduct_id(VALID_PRODUCT_ID);
    ArrayList<ProductEntity> el = new ArrayList<>();
    el.add(e);
    return el;
  }

  private Optional<ProductEntity> getProductEntity2() {
    ProductEntity e = new ProductEntity();
    e.setBrand("SAMSUNG");
    e.setColor(ProductColor.BLACK);
    e.setPrice(10000);
    e.setDescription("Samsung M30 Black");
    e.setTitle("Samsung M30");
    e.setProduct_id(VALID_PRODUCT_ID);
    return Optional.of(e);
  }

  private Optional<Product> getProduct() {
    Product e = new Product();
    e.setBrand("SAMSUNG");
    e.setColor(ProductColor.BLACK);
    e.setPrice(10000);
    e.setDescription("Samsung M30 Black");
    e.setTitle("Samsung M30");
    e.setProductId(VALID_PRODUCT_ID);
    return Optional.of(e);
  }


}
