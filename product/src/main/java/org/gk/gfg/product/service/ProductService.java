package org.gk.gfg.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.gk.gfg.product.exception.ProductNotFoundException;
import org.gk.gfg.product.model.PaginationRequest;
import org.gk.gfg.product.model.Product;
import org.gk.gfg.product.model.ProductEntity;
import org.gk.gfg.product.model.ResponseWrapper;
import org.gk.gfg.product.model.SearchProductDto;
import org.gk.gfg.product.repository.ProductRepository;
import org.gk.gfg.product.search.ProductServiceHelper;
import org.gk.gfg.product.search.SpecificationSearchCriteriaCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepo;

  @Autowired
  private SpecificationSearchCriteriaCreator specSearchCriteriaCreator;

  public List<Product> create(final List<Product> products) {
    try {
      List<ProductEntity> productEntities = new ArrayList<>();
      for (Product product : products) {
        product.setCreated(new Date());
        product.setModified(new Date());
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

  public Product get(final String productId) throws ProductNotFoundException {
    if (StringUtils.isBlank(productId))
      throw new ProductNotFoundException("invalid.input");
    final Optional<ProductEntity> entities = productRepo.findById(productId);
    return new Product(entities.get());
  }

  public Product update(final Product product, final String productId)
      throws ProductNotFoundException {
    if (StringUtils.isBlank(productId))
      throw new ProductNotFoundException("invalid.input");
    final Optional<ProductEntity> entities = productRepo.findById(productId);
    if (entities.isPresent()) {
      ProductEntity retrievedEntity = entities.get();
      updateProperties(retrievedEntity, product);
      final ProductEntity updatedEntity = productRepo.save(retrievedEntity);
      return new Product(updatedEntity);
    } else {
      throw new ProductNotFoundException("product.not.found");
    }
  }

  public Set<Product> update(final Set<Product> products) throws ProductNotFoundException {
    if (products == null || products.isEmpty())
      throw new ProductNotFoundException("invalid.input");
    final Set<String> productIds = new HashSet<>();
    products.stream().forEach(v -> productIds.add(v.getProductId()));


    final List<ProductEntity> productEntities = productRepo.findAllById(productIds);
    if (productEntities.size() == 0) {
      throw new ProductNotFoundException("entity.not.found");
    }
    for (final ProductEntity entity : productEntities) {
      final Product product = products.stream()
          .filter(v -> (v.getProductId().compareTo(entity.getProduct_id()) == 0)).findFirst().get();
      updateProperties(entity, product);
    }

    final List<ProductEntity> updatedEntity = productRepo.saveAll(productEntities);
    final Set<Product> res = new HashSet<>();
    updatedEntity.stream().forEach(v -> res.add(new Product(v)));
    return res;
  }

  public ResponseWrapper<Product> findProduct(final SearchProductDto searchDto,
      final PaginationRequest paginationRequest) throws ProductNotFoundException {
    final String whereClause = searchDto.getWhere();
    final ResponseWrapper<Product> res = search(searchDto, paginationRequest, whereClause);
    return res;
  }

  public void delete(final String productId) throws ProductNotFoundException {
    if (StringUtils.isBlank(productId))
      throw new ProductNotFoundException("invalid.input");
    productRepo.deleteById(productId);
  }

  private ResponseWrapper<Product> search(final SearchProductDto search,
      final PaginationRequest paginationRequest, final String searchClause)
      throws ProductNotFoundException {
    final Pageable pageable = ProductServiceHelper.preparePageable(search, paginationRequest);
    final Specification<ProductEntity> spec = ProductServiceHelper
        .<ProductEntity>resolveSpecification(searchClause, specSearchCriteriaCreator);
    final Page<ProductEntity> obtainedEntities = productRepo.findAll(spec, pageable);
    final List<Product> results = new ArrayList<>();
    obtainedEntities.stream().forEach(o -> results.add(new Product(o)));
    final ResponseWrapper<Product> res = new ResponseWrapper<>();
    final Long count = paginationRequest.isIncludeCount() ? productRepo.count(spec) : null;
    res.setCount(count);
    res.setResult(results);
    return res;
  }


  private void updateProperties(final ProductEntity entity, final Product product) {
    if (StringUtils.isNotBlank(product.getBrand()))
      entity.setBrand(product.getBrand());
    if (StringUtils.isNotBlank(product.getTitle()))
      entity.setTitle(product.getTitle());
    if (StringUtils.isNotBlank(product.getDescription()))
      entity.setDescription(product.getDescription());
    if (product.getPrice() != null)
      entity.setPrice(product.getPrice());
    if (StringUtils.isNotBlank(product.getColor()))
      entity.setColor(product.getColor());
    entity.setModified(new Date());
  }
}

