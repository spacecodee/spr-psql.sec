package com.spacecodee.sprpsqlsec.persistence.repository;

import com.spacecodee.sprpsqlsec.persistence.entity.security.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperationRepository extends JpaRepository<OperationEntity, Integer> {
    @Query("SELECT o FROM OperationEntity o WHERE o.permitAll = true")
    List<OperationEntity> findByPublicAccess();
}