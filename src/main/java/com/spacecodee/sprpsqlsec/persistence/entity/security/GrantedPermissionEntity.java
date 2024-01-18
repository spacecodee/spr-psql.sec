package com.spacecodee.sprpsqlsec.persistence.entity.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "permission", schema = "public", catalog = "database_sec")
public class GrantedPermissionEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleId;
    @ManyToOne()
    @JoinColumn(name = "operation_id", nullable = false)
    private OperationEntity operationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantedPermissionEntity that = (GrantedPermissionEntity) o;
        return id == that.id && roleId == that.roleId && operationId == that.operationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, operationId);
    }
}
