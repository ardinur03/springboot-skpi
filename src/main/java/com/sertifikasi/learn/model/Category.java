package com.sertifikasi.learn.model;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category implements java.io.Serializable {
    private static final long serialVersionUID = -4700415331431971056L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_categories_category_id_seq")
    @SequenceGenerator(name = "generator_categories_category_id_seq", sequenceName = "categories_category_id_seq", schema = "public", allocationSize = 1)
    @Column(name = "category_id", unique = true, nullable = false)
    private int categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", length = 29)
    private Timestamp createdTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", length = 29)
    private Timestamp modifiedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Recipe> recipeses;
}
