package com.example.sosikmemberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing

public class SosikMemberserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosikMemberserviceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder encoder (){
		return new BCryptPasswordEncoder();
	}


}
