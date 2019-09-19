

// PACKAGE/IMPORTS --------------------------------------------------
package org.gk.gfg.product.repository;

import org.gk.gfg.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

}

