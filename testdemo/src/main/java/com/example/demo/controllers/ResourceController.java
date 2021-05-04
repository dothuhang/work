package com.example.demo.controllers;

import com.example.demo.model.Resource;
import com.example.demo.services.MaterialServices;
import com.example.demo.services.ResourceServices;
import com.example.demo.tactmodel.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restapi/resources")
public class ResourceController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private ResourceServices services;

    @GetMapping("/all")
    public List<Resource> resourceList() {
        return services.findAll();
    }



}
