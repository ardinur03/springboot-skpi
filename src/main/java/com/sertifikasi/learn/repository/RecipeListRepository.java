package com.sertifikasi.learn.repository;

import com.sertifikasi.learn.model.Recipe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RecipeListRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
    long count(Specification<Recipe> spec);
}