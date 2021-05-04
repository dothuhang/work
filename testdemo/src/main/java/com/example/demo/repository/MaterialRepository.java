package com.example.demo.repository;

import com.example.demo.tactmodel.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT t FROM Material t WHERE t.ecomid = ?1")
    List<Material> findByProjectId(long key);

}
