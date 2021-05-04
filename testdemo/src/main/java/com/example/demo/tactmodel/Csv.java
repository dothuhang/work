package com.example.demo.tactmodel;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "raw", schema = "testdb")
public class Csv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "ecomid")
    private Long ecomid;
    @Column(name = "ecom_description")
    private String ecom_description;
    @Column(name = "client")
    private String client;
    @Column(name = "code_lvl_1")
    private String code_lvl_1;
    @Column(name = "code_lvl_2")
    private String code_lvl_2;
    @Column(name = "unit")
    private String unit;
    @Column(name = "description")
    private String description;
    @Column(name = "qte")
    private Float qte;
    @Column(name = "ht")
    private Float ht;
    @Column(name = "ds")
    private Float ds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getEcomid() {
        return ecomid;
    }

    public void setEcomid(Long ecomid) {
        this.ecomid = ecomid;
    }

    public String getEcom_description() {
        return ecom_description;
    }

    public void setEcom_description(String ecom_description) {
        this.ecom_description = ecom_description;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCode_lvl_1() {
        return code_lvl_1;
    }

    public void setCode_lvl_1(String code_lvl_1) {
        this.code_lvl_1 = code_lvl_1;
    }

    public String getCode_lvl_2() {
        return code_lvl_2;
    }

    public void setCode_lvl_2(String code_lvl_2) {
        this.code_lvl_2 = code_lvl_2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getQte() {
        return qte;
    }

    public void setQte(Float qte) {
        this.qte = qte;
    }

    public Float getHt() {
        return ht;
    }

    public void setHt(Float ht) {
        this.ht = ht;
    }

    public Float getDs() {
        return ds;
    }

    public void setDs(Float ds) {
        this.ds = ds;
    }
}
