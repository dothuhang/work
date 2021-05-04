package com.example.demo.services;

import com.example.demo.model.Plan;
import com.example.demo.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServices {
    @Autowired
    private PlanRepository repo;

    public List<Plan> findByProjectId(long ecomid) {
        return repo.findByProjectId(ecomid);
    };

    public List<Plan> findSubTaskByEcomId(long ecomid) {
        return repo.findSubTaskByEcomId(ecomid);
    };

    public List<Plan> findMaterialByEcomId(long ecomid) {
        return repo.findMaterialByEcomId(ecomid);
    };

    public void deleteById(long id) {
        repo.deleteById(id);
    };

    public List<Plan> makePlan(long ecomid) {
        repo.makePlan(ecomid);

        List<Plan> list = repo.findByProjectId(ecomid);

        return list;
    }

    public Plan save(Plan plan) {
        return repo.save(plan);
    }






}
