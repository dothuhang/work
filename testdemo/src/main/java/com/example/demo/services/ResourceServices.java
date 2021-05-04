package com.example.demo.services;

import com.example.demo.model.Resource;
import com.example.demo.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServices {
    @Autowired
    private ResourceRepository repo;

    public List<Resource> findAll() {
        return repo.findAll();
    }


}
