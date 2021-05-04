package com.example.demo.tactmodel;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "projects", schema = "testdb")
public class Ecom {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "userid")
    private Long userid;
    @Column(name = "description")
    private String description;
    @Column(name = "date_creation")
    private Date date_creation;
    @Column(name = "ht")
    private Float ht;
    @Column(name = "ds")
    private Float ds;
    @Column(name = "client")
    private String client;
    @Column(name = "start_date_chantier")
    private Date start_date_chantier;
    @Column(name = "finish_date_chantier")
    private Date finish_date_chantier;
    @Column(name = "acompte_date")
    private Date acompte_date;
    @Column(name = "acompte_pct")
    private Float acompte_pct;
    @Column(name = "rembourse_min")
    private Float rembourse_min;
    @Column(name = "rembourse_max")
    private Float rembourse_max;
    @Column(name = "retenue")
    private Float retenue;
    @Column(name = "penalty")
    private Float penalty;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getStart_date_chantier() {
        return start_date_chantier;
    }

    public void setStart_date_chantier(Date start_date_chantier) {
        this.start_date_chantier = start_date_chantier;
    }

    public Date getFinish_date_chantier() {
        return finish_date_chantier;
    }

    public void setFinish_date_chantier(Date finish_date_chantier) {
        this.finish_date_chantier = finish_date_chantier;
    }

    public Date getAcompte_date() {
        return acompte_date;
    }

    public void setAcompte_date(Date acompte_date) {
        this.acompte_date = acompte_date;
    }

    public Float getAcompte_pct() {
        return acompte_pct;
    }

    public void setAcompte_pct(Float acompte_pct) {
        this.acompte_pct = acompte_pct;
    }

    public Float getRembourse_min() {
        return rembourse_min;
    }

    public void setRembourse_min(Float rembourse_min) {
        this.rembourse_min = rembourse_min;
    }

    public Float getRembourse_max() {
        return rembourse_max;
    }

    public void setRembourse_max(Float rembourse_max) {
        this.rembourse_max = rembourse_max;
    }

    public Float getRetenue() {
        return retenue;
    }

    public void setRetenue(Float retenue) {
        this.retenue = retenue;
    }

    public Float getPenalty() {
        return penalty;
    }

    public void setPenalty(Float penalty) {
        this.penalty = penalty;
    }
}
