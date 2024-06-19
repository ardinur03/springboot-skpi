package com.sertifikasi.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertifikasi.learn.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
