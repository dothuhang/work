package com.example.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "finance", schema = "testdb")
public class Finance {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "ecomid")
    private Long ecomid;
    @Column(name = "code_lvl_1")
    private Long code_lvl_1;
    @Column(name = "code_lvl_2")
    private Long code_lvl_2;
    @Column(name = "cost")
    private Float cost;
    @Column(name = "acompte")
    private Float acompte;
    @Column(name = "rembourse")
    private Float rembourse;
    @Column(name = "facture")
    private Float facture;
    @Column(name = "penalty")
    private Float penalty;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getEcomid() {
        return ecomid;
    }

    public void setEcomid(Long ecomid) {
        this.ecomid = ecomid;
    }

    public Long getCode_lvl_1() {
        return code_lvl_1;
    }

    public void setCode_lvl_1(Long code_lvl_1) {
        this.code_lvl_1 = code_lvl_1;
    }

    public Long getCode_lvl_2() {
        return code_lvl_2;
    }

    public void setCode_lvl_2(Long code_lvl_2) {
        this.code_lvl_2 = code_lvl_2;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getAcompte() {
        return acompte;
    }

    public void setAcompte(Float acompte) {
        this.acompte = acompte;
    }

    public Float getRembourse() {
        return rembourse;
    }

    public void setRembourse(Float rembourse) {
        this.rembourse = rembourse;
    }

    public Float getFacture() {
        return facture;
    }

    public void setFacture(Float facture) {
        this.facture = facture;
    }

    public Float getPenalty() {
        return penalty;
    }

    public void setPenalty(Float penalty) {
        this.penalty = penalty;
    }
}
