package com.sertifikasi.learn.model;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Recipe implements java.io.Serializable {
    private static final long serialVersionUID = 90177031282210259L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_recipes_recipe_id_seq")
    @SequenceGenerator(name = "generator_recipes_recipe_id_seq", sequenceName = "recipes_recipe_id_seq", schema = "public", allocationSize = 1)
    @Column(name = "recipe_id", unique = true, nullable = false)
    private int recipeId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level levels;

    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "image_filename")
    private String imageFilename;

    @Column(name = "time_cook")
    private Integer timeCook;

    @Column(name = "ingridient")
    private String ingridient;

    @Column(name = "how_to_cook")
    private String howToCook;

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

    // @OneToMany(mappedBy = "recipes")
    // private Set<FavoriteFoods> favoriteFoodses;
}
