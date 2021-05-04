package com.example.demo.services;


import com.example.demo.model.CsvHandler;
import com.example.demo.repository.CsvRepository;
import com.example.demo.repository.EcomRepository;
import com.example.demo.tactmodel.Csv;
import com.example.demo.tactmodel.Ecom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class CsvServices {

    @Autowired
    private CsvRepository repo;
    @Autowired
    private EcomRepository ecomRepo;

    public boolean saveFile(InputStream file) {

        try {
            List<Csv> list = CsvHandler.parseCsvFile(file);
            Csv c = list.get(0);

            if (!ecomRepo.findByProjectId(c.getEcomid()).isPresent()) {
                Ecom ecom = new Ecom();

                repo.saveAll(list);
                ecom.setId(c.getEcomid());
                ecom.setDescription(c.getEcom_description());
                ecom.setClient(c.getClient());
                float ht = 0;
                float ds = 0;
                for (Csv l: list) {
                    ht += l.getHt();
                    ds += l.getDs();
                }
                ecom.setHt(ht);
                ecom.setDs(ds);
                ecomRepo.save(ecom);
                ecomRepo.postUploadCsv(ecom.getId());
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return false;

    }

    /*public void saveFile(InputStream file) {
        try {
            List<Csv> list = CsvHandler.parseCsvFile(file);
            Csv c = list.get(0);

            if (!ecomRepo.findByProjectId(c.getEcomid()).isPresent()) {
                Ecom ecom = new Ecom();

                repo.saveAll(list);
                ecom.setId(c.getEcomid());
                ecom.setDescription(c.getEcom_description());
                ecom.setClient(c.getClient());
                float ht = 0;
                float ds = 0;
                for (Csv l: list) {
                    ht += l.getHt();
                    ds += l.getDs();
                }
                ecom.setHt(ht);
                ecom.setDs(ds);
                ecomRepo.save(ecom);
                ecomRepo.postUploadCsv(ecom.getId());

            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }*/


}
