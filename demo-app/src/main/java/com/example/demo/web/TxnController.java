package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Txn;
import com.example.demo.repository.TxnRepository;

@RestController
public class TxnController {

	@Autowired
	private TxnRepository txnRepository;

	@GetMapping(value = "/txn-statement")
	public List<Txn> getTxns(Model model) {
		List<Txn> txns = txnRepository.findAll();
		return txns;
	}

}
