package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TxrService;

@RestController
public class TxrController {

	@Autowired
	private TxrService txrService;

	@RequestMapping(value = "/txr", method = RequestMethod.POST)
	public TxrResponse doTxr(@RequestBody TxrRequest request, Model model) {
		boolean b = txrService.transfer(request.getAmount(), request.getFromAccNum(), request.getToAccNum());
		TxrResponse response = new TxrResponse();
		response.setMessage(b ? "Txr Successfull" : "Txr Failed");
		return response;
	}

}
