package com.example.demo.services;

import com.example.demo.model.Plan;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.tactmodel.Material;
import com.example.demo.tactmodel.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServices {

    @Autowired
    private MaterialRepository repo;
    @Autowired
    private PlanRepository planRepo;

    public List<Material> findByProjectId(long key) {
        return repo.findByProjectId(key);
    };

    public List<Material> findLeftOverByProjectId(long ecomid) {
        List<Plan> planList = planRepo.findByProjectId(ecomid);
        List<Material> list = repo.findByProjectId(ecomid);

        for (Material t: list) {
            for (Plan p: planList) {
                if (p.getParent() == t.getId()) {
                    t.setQuantity(t.getQuantity() - p.getQuantity());
                }
            }
        }

        return list;
    }



}
