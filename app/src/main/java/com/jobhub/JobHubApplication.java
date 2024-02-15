package com.jobhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobHubApplication.class, args);
    }
}
