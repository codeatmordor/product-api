package org.gk.gfg.product.search;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecificationHelper {

  public Predicate createPredicate(final Root<?> root, final CriteriaBuilder builder,
      final SpecificationSearchCriteria criteria) {
    Expression<String> path = root.get(criteria.getKey());
    switch (criteria.getOperation()) {
      case EQUAL:
        return builder.equal(path, criteria.getValue());
      case NOTEQUAL:
        return builder.notEqual(path, criteria.getValue());
      case GREATER_THAN:
        return builder.greaterThan(path, criteria.getValue().toString());
      case LESS_THAN:
        return builder.lessThan(path, criteria.getValue().toString());
      case IN:
        return root.get(criteria.getKey()).in((List<String>) criteria.getValue());
      case NOTIN:
        return builder.not(root.get(criteria.getKey()).in((List<String>) criteria.getValue()));
      case LIKE:
        return builder.like(path, criteria.getValue().toString());
      case STARTS_WITH:
        return builder.like(path, criteria.getValue() + "%");
      case ENDS_WITH:
        return builder.like(path, "%" + criteria.getValue());
      case CONTAINS:
        return builder.like(path, "%" + criteria.getValue() + "%");
      default:
        return null;
    }

  }

}
