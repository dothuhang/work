package com.example.demo.controllers;

import com.example.demo.model.Plan;
import com.example.demo.services.PlanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restapi/plan")
public class PlanController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private PlanServices planServices;

    @GetMapping("/all_by_ecomid/{id}")
    public List<Plan> allPlan(@PathVariable(value = "id") long id) {
        return planServices.findByProjectId(id);
    }

    @GetMapping("/all_task_by_ecomid/{id}")
    public List<Plan> allTask(@PathVariable(value = "id") long id) {
        return planServices.findSubTaskByEcomId(id);
    }

    @GetMapping("/all_material_by_ecomid/{id}")
    public List<Plan> allMaterial(@PathVariable(value = "id") long id) {
        return planServices.findMaterialByEcomId(id);
    }


    @PostMapping("/save")
    public Plan savePlanMaterial(@RequestBody Plan plan) {

        return planServices.save(plan);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePlan(@PathVariable(value = "id") long id) {
        planServices.deleteById(id);

    }

    @PutMapping("/update/{id}")
    public void updatePlan(@RequestBody Plan p) {
        planServices.save(p);
    }

    @GetMapping("/make_plan/{id}")
    public List<Plan> makePlan(@PathVariable(value = "id") long id) {
        return planServices.makePlan(id);
    }

}
