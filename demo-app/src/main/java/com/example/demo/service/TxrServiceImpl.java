package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Account;
import com.example.demo.model.Txn;
import com.example.demo.model.TxnType;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TxnRepository;

@Service
public class TxrServiceImpl implements TxrService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TxnRepository txnRepository;

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void setTxnRepository(TxnRepository txnRepository) {
		this.txnRepository = txnRepository;
	}

	@Transactional(transactionManager = "transactionManager", timeout = 60, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public boolean transfer(double amount, String fromAccNum, String toAccNum) {

		Account fromAccount = accountRepository.findById(fromAccNum).get();
		Account toAccount = accountRepository.findById(toAccNum).get();

		if (fromAccount != null && toAccount != null) {
			
			
			if(fromAccount.getBalance()<=amount)
				throw new AccountBalanceException("isufficient funds");
			
			fromAccount.setBalance(fromAccount.getBalance() - amount);
			toAccount.setBalance(toAccount.getBalance() + amount);

			
			
			accountRepository.save(fromAccount);
			accountRepository.save(toAccount);

			Txn debitTxn = new Txn();
			debitTxn.setAccount(fromAccount);
			debitTxn.setAmount(amount);
			debitTxn.setClosingBalance(fromAccount.getBalance());
			debitTxn.setType(TxnType.DEBIT);
			debitTxn.setLocalDateTime(LocalDateTime.now());

			Txn creditTxn = new Txn();
			creditTxn.setAccount(toAccount);
			creditTxn.setAmount(amount);
			creditTxn.setClosingBalance(toAccount.getBalance());
			creditTxn.setType(TxnType.CREDIT);
			creditTxn.setLocalDateTime(LocalDateTime.now());

			txnRepository.save(debitTxn);
			txnRepository.save(creditTxn);

			return true;
		}

		return false;

	}

}
