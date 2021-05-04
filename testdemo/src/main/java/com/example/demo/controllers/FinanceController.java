package com.example.demo.controllers;


import com.example.demo.model.FinanceGroupBy;
import com.example.demo.services.FinanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restapi/finance")
public class FinanceController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private FinanceServices financeServices;

    @GetMapping("/all_by_ecomid/{id}")
    public List<FinanceGroupBy> plan(@PathVariable(value = "id") long id) {
        return financeServices.groupByProjectId(id);
    }



}
