package com.proper.classes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
//@EnableReactiveMongoRepositories(basePackages = "com.proper.classes.repository")
public class ProperClassesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProperClassesApplication.class, args);
    }
}
