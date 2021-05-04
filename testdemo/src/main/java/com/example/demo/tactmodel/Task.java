package com.example.demo.tactmodel;


import javax.persistence.*;

@Entity
@Table(name = "tasks", schema = "testdb")
public class Task {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "ecomid")
    private Long ecomid;
    @Column(name = "code_lvl_1")
    private String code_lvl_1;
    @Column(name = "code_lvl_2")
    private String code_lvl_2;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private Float quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
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

    public Float getMargin() {
        return ht/ds-1;
    }
}
