package com.lgwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring4all.swagger.EnableSwagger2Doc;


/**
 * main
 * @author irays
 *
 */
@EnableSwagger2Doc
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class RbacSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbacSecurityApplication.class, args);
	}
	
}
