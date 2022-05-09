package com.guavapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsIdentityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsIdentityApplication.class, args);
	}

}
