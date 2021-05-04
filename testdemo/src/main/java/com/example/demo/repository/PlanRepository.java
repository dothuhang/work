package com.example.demo.repository;

import com.example.demo.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p FROM Plan p WHERE p.ecomid = ?1")
    List<Plan> findByProjectId(long key);

    @Query("SELECT p FROM Plan p WHERE p.ecomid=?1 AND p.code_lvl_2 IS NOT NULL")
    List<Plan> findSubTaskByEcomId(long id);

    @Query("SELECT p FROM Plan p WHERE p.ecomid=?1 AND p.code_lvl_2 IS NULL")
    List<Plan> findMaterialByEcomId(long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Plan p WHERE p.id = ?1")
    void deleteById(long id);

    @Query(value = "call make_plan(:id);", nativeQuery = true)
    void makePlan(@Param("id") long id);

    @Query(value = "call calculate_finance_plan(:id);", nativeQuery = true)
    void calculateFinancePlan(@Param("id") long id);



}
