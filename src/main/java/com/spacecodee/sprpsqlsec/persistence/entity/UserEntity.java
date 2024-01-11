package com.spacecodee.sprpsqlsec.persistence.entity;

import com.spacecodee.sprpsqlsec.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "\"user\"", schema = "public", catalog = "database_sec")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "username", nullable = false)
    private String username;
    @Basic
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleEnum role;
}
