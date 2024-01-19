package com.spacecodee.sprpsqlsec.persistence.entity;

import com.spacecodee.sprpsqlsec.persistence.entity.security.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "jwt_token", schema = "public", catalog = "database_sec")
public class JwtTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private long id;
    @Column(name = "token", nullable = false, length = 2048)
    private String token;
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
    @Column(name = "is_valid", nullable = false)
    private boolean isValid;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
}
