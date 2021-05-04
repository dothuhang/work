package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "resources", schema = "testdb")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "description")
    private String description;
    @Column(name = "skill")
    private String skill;
    @Column(name = "limit_hours_per_week")
    private Float limit_hours_per_week;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Float getLimit_hours_per_week() {
        return limit_hours_per_week;
    }

    public void setLimit_hours_per_week(Float limit_hours_per_week) {
        this.limit_hours_per_week = limit_hours_per_week;
    }
}
