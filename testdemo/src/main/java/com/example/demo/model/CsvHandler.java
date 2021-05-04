package com.example.demo.model;

import com.example.demo.tactmodel.Csv;
import com.example.demo.tactmodel.Ecom;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {

    private static String csvExtension = "csv";

    public static List<Csv> parseCsvFile(InputStream is) {
        String[] header = {"ecomid", "ecom_description", "client", "code_lvl_1", "code_lvl_2", "unit", "description", "qte", "ht", "ds"};
        Reader fileReader = null;
        CsvToBean<Csv> csvToBean = null;

        List<Csv> lines = new ArrayList<Csv>();

        try {
            fileReader = new InputStreamReader(is);
            ColumnPositionMappingStrategy<Csv> mappingStrategy = new ColumnPositionMappingStrategy<Csv>();

            mappingStrategy.setType(Csv.class);
            mappingStrategy.setColumnMapping(header);

            csvToBean = new CsvToBeanBuilder<Csv>(fileReader).withMappingStrategy(mappingStrategy)
                                .withSkipLines(1)
                                .withIgnoreLeadingWhiteSpace(true)
                                .withSeparator(';').build();
            lines = csvToBean.parse();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static boolean isCsv(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1];

        if (!extension.equals(csvExtension)) {
            return false;
        }
        return true;
    }


}
