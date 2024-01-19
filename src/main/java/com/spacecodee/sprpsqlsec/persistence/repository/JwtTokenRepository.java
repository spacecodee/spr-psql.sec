package com.spacecodee.sprpsqlsec.persistence.repository;

import com.spacecodee.sprpsqlsec.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
}