package com.example.demo.repository;

import com.example.demo.model.Finance;
import com.example.demo.model.FinanceGroupBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {

    @Query(value = "SELECT p.date, SUM(p.cost) AS cost, " +
            "SUM(p.acompte) AS acompte, " +
            "SUM(p.rembourse) AS rembourse, " +
            "SUM(p.facture) AS facture, " +
            "SUM(p.penalty) AS penalty, " +
            "SUM(p.facture + p.acompte - p.cost - p.rembourse - p.penalty) AS state " +
            "FROM Finance p WHERE p.ecomid = ?1 GROUP BY p.date ORDER BY p.date", nativeQuery = true)
    List<FinanceGroupBy> groupByProjectId(long ecomid);


    @Query(value = "SELECT p.date, SUM(p.cost) AS cost, " +
            "SUM(p.acompte) AS acompte, " +
            "SUM(p.rembourse) AS rembourse, " +
            "SUM(p.facture) AS facture, " +
            "SUM(p.penalty) AS penalty, " +
            "SUM(p.facture + p.acompte - p.cost - p.rembourse - p.penalty) AS state " +
            "FROM Finance p WHERE p.ecomid = :id AND p.code_lvl_1 = :code GROUP BY p.date ORDER BY p.date", nativeQuery = true)
    List<FinanceGroupBy> groupByCode_lvl_1(@Param(value = "id") long ecomid, @Param(value = "code") String code);

}
