package org.gk.gfg.product.search;

public interface SpecificationSearchCriteriaCreator {

    SpecificationSearchCriteria create(final String key, final String operation, final String prefix, final String value, final String suffix);
}
