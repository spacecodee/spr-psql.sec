package com.spacecodee.sprpsqlsec.persistence.repository;

import com.spacecodee.sprpsqlsec.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
