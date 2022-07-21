package com.unicomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnicomerApplication {

    /*
    Using lombok annotation for workaround for code coverage.
     */
    @lombok.Generated
    public static void main(String[] args) {
        SpringApplication.run(UnicomerApplication.class);
    }

}
