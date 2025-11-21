package com.smarttask.smart_task_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.smarttask.smart_task_manager")
@EnableJpaAuditing
public class SmartTaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartTaskManagerApplication.class, args);
	}

}
