package com.example.demo.tactmodel;

import javax.persistence.*;

@Entity
@Table(name = "materials", schema = "testdb")
public class Material {
    @Id
    protected long id;
    @Column(name = "ecomid")
    protected Long ecomid;
    @Column(name = "code")
    protected String code;
    @Column(name = "description")
    protected String description;
    @Column(name = "quantity")
    protected Float quantity;
    @Column(name = "ht")
    protected Float ht;
    @Column(name = "ds")
    protected Float ds;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
