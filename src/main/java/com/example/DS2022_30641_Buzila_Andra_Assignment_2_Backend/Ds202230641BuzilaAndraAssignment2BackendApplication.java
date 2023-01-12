package com.example.DS2022_30641_Buzila_Andra_Assignment_2_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class Ds202230641BuzilaAndraAssignment2BackendApplication {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
		SpringApplication.run(Ds202230641BuzilaAndraAssignment2BackendApplication.class, args);
		MessageProducer producer = new MessageProducer();
		producer.produceMessage();
	}

}
