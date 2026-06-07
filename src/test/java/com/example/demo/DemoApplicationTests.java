package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testApplicationInstantiation() {
		DemoApplication app = new DemoApplication();
		assertNotNull(app);
	}

	@Test
	void testMainMethod() {
		DemoApplication.main(new String[]{"--spring.main.web-application-type=none"});
	}
}

