package com.gszcn.aptrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AptrunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AptrunnerApplication.class, args);
    }

}
