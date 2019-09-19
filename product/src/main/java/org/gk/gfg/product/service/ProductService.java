

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.service;

import java.util.ArrayList;
import java.util.List;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.model.ProductEntity;
import org.gk.gfg.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepo;


  public List<Product> create(final List<Product> products) {
    try {
      List<ProductEntity> productEntities = new ArrayList<>();
      for (Product product : products) {
        productEntities.add(new ProductEntity(product));
      }
      final List<ProductEntity> createdEntities = productRepo.saveAll(productEntities);
      final List<Product> res = new ArrayList<>();
      createdEntities.stream().forEach(c -> res.add(new Product(c)));
      return res;
    } catch (final Exception e) {
      return null;
    }
  }



}

