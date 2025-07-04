package com.sportresult;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableFeignClients
public class InjectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InjectorApplication.class, args);
	}
}
