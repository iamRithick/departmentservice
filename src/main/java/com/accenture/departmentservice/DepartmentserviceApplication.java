package com.accenture.departmentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DepartmentserviceApplication {

    private static final Logger log = LoggerFactory.getLogger(DepartmentserviceApplication.class);

    public static void main(String[] args) {
        log.info("Starting Department Service");
        SpringApplication.run(DepartmentserviceApplication.class, args);
    }

}
