package com.example.demo.services;

import com.example.demo.model.Finance;
import com.example.demo.model.FinanceGroupBy;
import com.example.demo.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceServices {
    @Autowired
    private FinanceRepository repo;

    public List<FinanceGroupBy> groupByProjectId(long ecomid) {
        return repo.groupByProjectId(ecomid);
    }




}
