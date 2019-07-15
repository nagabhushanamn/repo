package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TxnRepository;

import static org.mockito.Mockito.when;

import java.util.Optional;

@SpringBootTest // Spring based Test Runner
public class TxrServiceUnitTest {

	@Mock
	AccountRepository accountRepository;

	@Mock
	TxnRepository txnRepository;

	@InjectMocks
	TxrService txrService = new TxrServiceImpl(); // Unit...

	@BeforeEach
	void setMockOutput() {
		Account fromAccount = new Account("111", 1000.00);
		Account toAccount = new Account("222", 1000.00);
		when(accountRepository.findById("111")).thenReturn(Optional.of(fromAccount));
		when(accountRepository.findById("222")).thenReturn(Optional.of(toAccount));
	}

	@Test
	public void txrTest() {
		boolean actual = txrService.transfer(100.00, "111", "222");
		assertTrue(actual);
	}

	@Test
	public void txrWithMaxAmount() {
		assertThrows(AccountBalanceException.class, () -> {
			txrService.transfer(2000.00, "111", "222");
		});
	}

}
