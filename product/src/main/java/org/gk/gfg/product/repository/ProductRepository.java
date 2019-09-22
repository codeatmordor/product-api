

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.repository;

import org.gk.gfg.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository
    extends JpaRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {
}

