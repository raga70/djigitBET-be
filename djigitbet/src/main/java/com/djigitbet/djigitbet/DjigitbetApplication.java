package com.djigitbet.djigitbet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.djigitbet.djigitbet.Services","com.djigitbet.djigitbet.Controller"})
@EntityScan("com.djigitbet.djigitbet.Entity")
@EnableJpaRepositories("com.djigitbet.djigitbet.DataAcessLayer")
public class DjigitbetApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DjigitbetApplication.class, args);
	}

}
