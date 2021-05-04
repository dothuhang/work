package com.example.demo.controllers;

import com.example.demo.model.Plan;
import com.example.demo.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/home/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/home/projects/{id}")
    public String projectDetail(@PathVariable(name="id") long id) {
        return "projectDetail";
    }

    @GetMapping("/home/projects/{id}/edit_task")
    public String editTask(@PathVariable(name="id") long id) {
        return "editTask";
    }

    @GetMapping("/home/projects/{id}/edit_material")
    public String editMaterial(@PathVariable(name="id") long id) {
        return "editMaterial";
    }

    @GetMapping("/home/projects/{id}/plan")
    public String plan(@PathVariable(name="id") long id) {
        return "plan";
    }

    @GetMapping("/home/upload")
    public String upload() {
        return "uploadCsv";
    }


}
