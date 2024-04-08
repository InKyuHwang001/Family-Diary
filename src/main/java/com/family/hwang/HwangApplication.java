package com.family.hwang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HwangApplication {

	public static void main(String[] args) {
		SpringApplication.run(HwangApplication.class, args);
	}

}
