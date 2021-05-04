package com.example.demo;

import com.example.demo.repository.EcomRepository;
import com.example.demo.tactmodel.Ecom;
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
public class EcomRepositoryTests {
    @Autowired
    private EcomRepository repo;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testFindByUsername() {
        List<Ecom> list = repo.findByUsername("hangdt");
        Assert.assertEquals(1, list.size());
        System.out.println(list.get(0).getDescription());
    }

    @Test
    public void testFindByProjectId() {
        Ecom ecom = repo.findByProjectId(484).get();
        Assert.assertEquals(484, ecom.getId());
    }

    @Test
    public void testFindAll() {
        List<Ecom> l = repo.findAll();
        Assert.assertEquals(545 ,l.size());
    }
}
