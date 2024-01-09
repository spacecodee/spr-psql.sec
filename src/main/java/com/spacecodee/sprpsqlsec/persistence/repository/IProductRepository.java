package com.spacecodee.sprpsqlsec.persistence.repository;

import com.spacecodee.sprpsqlsec.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
}
