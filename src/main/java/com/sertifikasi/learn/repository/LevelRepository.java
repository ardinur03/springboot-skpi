package com.sertifikasi.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertifikasi.learn.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

}
