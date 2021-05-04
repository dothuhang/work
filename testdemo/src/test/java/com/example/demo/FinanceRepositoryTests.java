package com.example.demo;

import com.example.demo.model.Finance;
import com.example.demo.model.FinanceGroupBy;
import com.example.demo.model.Plan;
import com.example.demo.repository.FinanceRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.tactmodel.Task;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FinanceRepositoryTests {
    @Autowired
    private FinanceRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGroupByProjectId() {
        List<FinanceGroupBy> data =  repo.groupByProjectId(484);
        for (FinanceGroupBy f: data) {
            System.out.print(f.getDate().toString() +": " + f.getState());
        }
    }



}
