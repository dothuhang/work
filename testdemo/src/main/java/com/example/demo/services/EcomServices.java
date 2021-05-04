package com.example.demo.services;

import com.example.demo.repository.EcomRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.tactmodel.Ecom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EcomServices {
    @Autowired
    private EcomRepository repo;

    public List<Ecom> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Optional<Ecom> findByProjectId(long id) {
        return repo.findByProjectId(id);
    };

    public Ecom save(Ecom ecom) {
        return repo.save(ecom);
    }

    public List<Ecom> findAll() {
        return repo.findAll();
    }



}
