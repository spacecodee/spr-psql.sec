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
@Table(name = "module", schema = "public", catalog = "database_sec")
public class ModuleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 25)
    private String name;
    @Basic
    @Column(name = "base_path", nullable = false, length = 100)
    private String basePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleEntity that = (ModuleEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(basePath, that.basePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, basePath);
    }
}
