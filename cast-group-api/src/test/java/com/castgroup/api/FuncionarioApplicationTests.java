package com.castgroup.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("application-qa")
public class FuncionarioApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("------------------------------->TESTE<---------------------------------------");
	}

}
