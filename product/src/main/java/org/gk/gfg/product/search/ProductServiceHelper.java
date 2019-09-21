package org.gk.gfg.product.search;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.gk.gfg.product.exception.ProductServiceException;
import org.gk.gfg.product.model.PaginationRequest;
import org.gk.gfg.product.model.SearchProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

public class ProductServiceHelper {
  public static <T> Specification<T> resolveSpecification(final String searchParameters,
      final SpecificationSearchCriteriaCreator specSearchCriteriaCreator)
      throws ProductServiceException {
    final CriteriaParser criteriaParser = new CriteriaParser();
    final ProductSpecificationBuilder<T> specificationBuilder = new ProductSpecificationBuilder<>();
    return specificationBuilder.build(
        criteriaParser.parse(searchParameters, specSearchCriteriaCreator),
        ProductSpecification<T>::new);
  }

  public static Pageable preparePageable(final SearchProductDto searchDto,
      PaginationRequest paginationRequest) {
    Sort sort = null;
    if (StringUtils.isNotBlank(searchDto.getSortBy())) {
      final List<String> sortByList = Arrays.asList(searchDto.getSortBy().split("\\s*,\\s*"));
      final Direction direction =
          searchDto.getSortOrder() != null && searchDto.getSortOrder().equalsIgnoreCase("desc")
              ? Sort.Direction.DESC
              : Sort.Direction.ASC;
      sort = new Sort(direction, sortByList.stream().toArray(String[]::new));
    }
    if (paginationRequest == null) {
      paginationRequest = new PaginationRequest(1000, 0);
    }
    final Pageable pageable = (sort != null)
        ? new OffsetBasedPageRequest(paginationRequest.getOffset(), paginationRequest.getLimit(),
            sort)
        : new OffsetBasedPageRequest(paginationRequest.getOffset(), paginationRequest.getLimit());
    return pageable;
  }
}
