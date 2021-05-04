package com.example.demo.repository;

import com.example.demo.tactmodel.Csv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.io.InputStream;

public interface CsvRepository extends JpaRepository<Csv, Long> {



}
