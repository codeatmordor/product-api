package org.gk.gfg.product.search;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.gk.gfg.product.exception.ProductNotFoundException;
import org.gk.gfg.product.model.PaginationRequest;
import org.gk.gfg.product.model.SearchProductDto;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

public class ProductServiceHelper {
  public static <T> Specification<T> resolveSpecification(final String searchParameters,
      final SpecificationSearchCriteriaCreator specSearchCriteriaCreator)
      throws ProductNotFoundException {
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

  @SuppressWarnings("unchecked")
  public static CriteriaQuery getDefaultCriteria(final Integer customerDomainId,
      @SuppressWarnings("rawtypes") final Class entityClass, final Session session,
      final Boolean deleted, final Integer offset, final Integer limit, final String orderBy,
      final String sortBy) {
    // Create CriteriaBuilder
    final CriteriaBuilder builder = session.getCriteriaBuilder();

    // Create CriteriaQuery
    final CriteriaQuery criteriaQuery = builder.createQuery(entityClass);
    //
    final Root root = criteriaQuery.from(entityClass);
    criteriaQuery.select(root);
    if (StringUtils.isEmpty(orderBy) && StringUtils.isEmpty(sortBy))
      criteriaQuery.orderBy(builder.asc(root.get("created")));

    if (StringUtils.isNotEmpty(sortBy)) {
      if (StringUtils.isEmpty(orderBy) || "ASC".equalsIgnoreCase(orderBy))
        criteriaQuery.orderBy(builder.asc(root.get(sortBy)));
      else if ("DESC".equalsIgnoreCase(orderBy))
        criteriaQuery.orderBy(builder.desc(root.get(sortBy)));
      else
        criteriaQuery.orderBy(builder.asc(root.get("created")));
    }

    if (Objects.nonNull(customerDomainId))
      criteriaQuery.where(builder.equal(root.get("customer_domain_id"), customerDomainId));

    if (Objects.nonNull(deleted))
      criteriaQuery.where(builder.equal(root.get("deleted"), deleted));
    return criteriaQuery;
  }

  public static Timestamp convertToTimestamp(final String date) throws ProductNotFoundException {
    if (date == null || date.isEmpty()) {
      return null;
    }
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return new Timestamp(dateFormat.parse(date).getTime());
    } catch (final ParseException parseException) {
      throw new ProductNotFoundException("invalid.date.format");
    }
  }

}
