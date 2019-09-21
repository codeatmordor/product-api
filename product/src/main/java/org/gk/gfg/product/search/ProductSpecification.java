
package org.gk.gfg.product.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification<C> implements Specification<C> {

  private static final long serialVersionUID = 7356023004931974231L;

  private final ProductSpecificationHelper productSpecificationHelper;

  private final SpecificationSearchCriteria criteria;

  public ProductSpecification(final SpecificationSearchCriteria criteria) {
    super();
    this.criteria = criteria;
    this.productSpecificationHelper = new ProductSpecificationHelper();
  }

  @Override
  public Predicate toPredicate(final Root<C> root, final CriteriaQuery<?> query,
      final CriteriaBuilder builder) {
    return productSpecificationHelper.createPredicate(root, builder, criteria);
  }

}
