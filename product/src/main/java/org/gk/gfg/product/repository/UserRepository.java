package org.gk.gfg.product.repository;

import org.gk.gfg.product.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

  UserEntity findByUsername(String username);

}
