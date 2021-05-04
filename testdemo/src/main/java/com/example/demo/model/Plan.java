package com.example.demo.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "plan", schema = "testdb")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "early_start")
    private Date early_start;
    @Column(name = "early_finish")
    private Date early_finish;
    @Column(name = "late_start")
    private Date late_start;
    @Column(name = "late_finish")
    private Date late_finish;
    @Column(name = "start_date")
    private Date start_date;
    @Column(name = "finish_date")
    private Date finish_date;
    @Column(name = "predecessors")
    private String predecessors;
    @Column(name = "time_lag")
    private String time_lag;
    @Column(name = "zone")
    private Integer zone;
    @Column(name = "parent")
    private Long parent;
    @Column(name = "resources")
    private String resources;


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

    public void setCode_lvl_1(String code) {
        this.code_lvl_1 = code;
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

    public Date getEarly_start() {
        return early_start;
    }

    public void setEarly_start(Date early_start) {
        this.early_start = early_start;
    }

    public Date getEarly_finish() {
        return early_finish;
    }

    public void setEarly_finish(Date early_finish) {
        this.early_finish = early_finish;
    }

    public Date getLate_start() {
        return late_start;
    }

    public void setLate_start(Date late_start) {
        this.late_start = late_start;
    }

    public Date getLate_finish() {
        return late_finish;
    }

    public void setLate_finish(Date late_finish) {
        this.late_finish = late_finish;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public String getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(String predecessors) {
        this.predecessors = predecessors;
    }

    public String getTime_lag() {
        return time_lag;
    }

    public void setTime_lag(String time_lag) {
        this.time_lag = time_lag;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getCode_lvl_2() {
        return code_lvl_2;
    }

    public void setCode_lvl_2(String code_lvl_2) {
        this.code_lvl_2 = code_lvl_2;
    }
}
