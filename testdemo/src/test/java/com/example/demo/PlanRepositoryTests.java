package com.example.demo;

import com.example.demo.model.Plan;
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
public class PlanRepositoryTests {
    @Autowired
    private PlanRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreatePlan() {


    }

    @Test
    public void testDeleteById() {
        Plan plan = new Plan();
        plan.setId(38);
        repo.deleteById(plan.getId());
    }


    @Test
    public void testFindByProjectId() {
        List<Plan> list = repo.findByProjectId(484);

        Assert.assertEquals(14, list.size());
    }



}
