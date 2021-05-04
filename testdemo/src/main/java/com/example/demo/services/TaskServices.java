package com.example.demo.services;

import com.example.demo.model.Plan;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.tactmodel.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskServices")
public class TaskServices {

    @Autowired
    private TaskRepository repo;
    @Autowired
    private PlanRepository planRepo;

    public List<Task> findByProjectId(long key) {
        return repo.findByProjectId(key);
    };

    public List<Task> findLeftOverByProjectId(long ecomid) {
        List<Plan> planList = planRepo.findByProjectId(ecomid);
        List<Task> list = repo.findByProjectId(ecomid);

        for (Task t: list) {
            for (Plan p: planList) {
                if (p.getParent() == t.getId()) {
                    t.setQuantity(t.getQuantity() - p.getQuantity());
                }
            }
        }

        return list;
    }



}
