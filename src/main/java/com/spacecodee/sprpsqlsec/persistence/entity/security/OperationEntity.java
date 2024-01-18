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
@Table(name = "operation", schema = "public", catalog = "database_sec")
public class OperationEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "tag", nullable = false, length = 50)
    private String tag;
    @Basic
    @Column(name = "path", nullable = false, length = 100)
    private String path;
    @Basic
    @Column(name = "http_method", nullable = false, length = 25)
    private String httpMethod;
    @Basic
    @Column(name = "permit_all", nullable = false)
    private boolean permitAll;
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity moduleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntity that = (OperationEntity) o;
        return id == that.id && permitAll == that.permitAll && moduleId == that.moduleId && Objects.equals(tag, that.tag) && Objects.equals(path, that.path) && Objects.equals(httpMethod, that.httpMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag, path, httpMethod, permitAll, moduleId);
    }
}
