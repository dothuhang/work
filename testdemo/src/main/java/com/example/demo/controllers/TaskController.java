package com.example.demo.controllers;

import com.example.demo.services.TaskServices;
import com.example.demo.tactmodel.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restapi/task")
public class TaskController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private TaskServices services;

    @GetMapping("/all_by_ecomid/{id}")
    public List<Task> taskList(@PathVariable(value = "id") long id) {
        return services.findLeftOverByProjectId(id);
    }



}
