package com.example.demo;

import com.example.demo.controllers.ProjectController;
import com.example.demo.controllers.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserController user;
	@Autowired
	private ProjectController project;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(project).isNotNull();
	}

}
