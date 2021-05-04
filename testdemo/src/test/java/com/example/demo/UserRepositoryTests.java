package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("thu-hang.do@dauphine.eu");
        user.setUsername("hangdt");
        user.setPassword("abc123456");

        User savedUser = repo.save(user);
        User existUser = entityManager.find(User.class, savedUser.getUserid());

        Assert.assertEquals(existUser.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "thu-hang.do@dauphine.eu";

        User user = repo.findByEmail(email);

        Assert.assertEquals("hangdt", user.getUsername());
    }

    @Test
    public void testFindUserByUsername() {
        String username = "hangdt";

        User user = repo.findByUsername(username);

        Assert.assertEquals("thu-hang.do@dauphine.eu", user.getEmail());
    }
}
