package com.ahmad.rest.webservices_cpe.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		System.out.println("Strating the whole Application");
		SpringApplication.run(RestfulWebServicesApplication.class, args);
		
//		//ReceiveMailWithAttachement.runReceiveMailThread();
//		ReceiveMailWithAttachement myThread = new ReceiveMailWithAttachement();
//		myThread.start();
	}

}
