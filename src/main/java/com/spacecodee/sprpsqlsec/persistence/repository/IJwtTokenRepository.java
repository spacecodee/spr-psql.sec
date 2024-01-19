package com.spacecodee.sprpsqlsec.persistence.repository;

import com.spacecodee.sprpsqlsec.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    Optional<JwtTokenEntity> findByToken(String jwt);
}