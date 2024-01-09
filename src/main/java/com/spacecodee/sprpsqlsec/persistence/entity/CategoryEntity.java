package com.spacecodee.sprpsqlsec.persistence.entity;

import com.spacecodee.sprpsqlsec.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category", schema = "public", catalog = "database_sec")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT")
    private long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CategoryStatus status;
}
