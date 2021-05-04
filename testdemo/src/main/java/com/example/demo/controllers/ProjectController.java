package com.example.demo.controllers;

import com.example.demo.services.ApiError;
import com.example.demo.services.CsvServices;
import com.example.demo.services.EcomServices;
import com.example.demo.services.CustomExceptionHandler;
import com.example.demo.tactmodel.Ecom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restapi/ecom")
public class ProjectController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private EcomServices ecomServices;

    @Autowired
    private CsvServices csvServices;

    @GetMapping("/all")
    public List<Ecom> projects(Principal user) {
//        return ecomRepo.findByUsername(user.getName());
        return ecomServices.findAll();
    }

    @GetMapping("/{id}")
    public Ecom projectId(@PathVariable(value="id") long id) {
        return ecomServices.findByProjectId(id).get();
    }

    @PutMapping("/{id}/update")
    public Ecom updateProject(@PathVariable(value="id") long id, @RequestBody Ecom ecomNew) {

        Ecom ecom = ecomServices.findByProjectId(id).get();
        ecom.setStart_date_chantier(ecomNew.getStart_date_chantier());
        ecom.setFinish_date_chantier(ecomNew.getFinish_date_chantier());
        ecom.setAcompte_date(ecomNew.getAcompte_date());
        ecom.setAcompte_pct(ecomNew.getAcompte_pct());
        ecom.setRembourse_min(ecomNew.getRembourse_min());
        ecom.setRembourse_max(ecomNew.getRembourse_max());
        ecom.setRetenue(ecomNew.getRetenue());
        ecom.setPenalty(ecomNew.getPenalty());

        Ecom updatedEcom = ecomServices.save(ecom);

        return updatedEcom;
    }

    @PostMapping("/uploadcsv")
    public ApiError uploadCsv(@RequestParam("file") MultipartFile csvfile) {
        ApiError response;
        if (csvfile.getOriginalFilename().isEmpty()) {
            response = new ApiError(HttpStatus.BAD_REQUEST,"Error: ", new Throwable("File missing!"));
            return response;
        }

        try {
            Boolean result = csvServices.saveFile(csvfile.getInputStream());
            response = new ApiError(HttpStatus.BAD_REQUEST,"Error: ", new Throwable("Existed project!"));
        } catch (Exception e) {
            response = new ApiError(HttpStatus.BAD_REQUEST,"Error: ", new Throwable(e.getMessage()));
        }
        return response;

    }

    /*@PostMapping("/uploadcsv")
    public ResponseEntity<Object> uploadCsv(@RequestParam("file") MultipartFile csvfile) {
        ResponseEntity<Object> response;
        if (csvfile.getOriginalFilename().isEmpty()) {
            response = new CustomExceptionHandler().handleHttpBadRequest("Error: ", new Throwable("File missing!"));
            return response;
        }

        try {
            csvServices.saveFile(csvfile.getInputStream());
            response = ResponseEntity.ok().build();
        } catch (Exception e) {
            response = new CustomExceptionHandler().handleHttpBadRequest("Error: ", new Throwable(e.getMessage()));
        }
        return response;

    }*/

}
