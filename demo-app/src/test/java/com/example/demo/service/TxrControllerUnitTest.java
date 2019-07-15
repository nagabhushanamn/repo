package com.example.demo.service;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.demo.web.TxrRequest;
import com.example.demo.web.TxrResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TxrControllerUnitTest {

	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getHello() throws Exception {
		URL url = new URL("http://localhost:" + port + "/txr");
		TxrRequest request = new TxrRequest();
		request.setAmount(1000.00);
		request.setFromAccNum("1");
		request.setToAccNum("2");
		ResponseEntity<TxrResponse> responseEntity = restTemplate.postForEntity(url.toString(), request,TxrResponse.class);
		TxrResponse txrResponse = responseEntity.getBody();
		assertEquals("Txr Successfull", txrResponse.getMessage());
	}

}
