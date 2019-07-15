package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TxrServiceIntegrationTest {

	@Autowired
	TxrService txrService;
	
	@Transactional
	@Test
	public void txrTest() {
		assertThrows(AccountBalanceException.class, ()->{
			txrService.transfer(2000.00, "1", "2");
		});
	}
	
}
