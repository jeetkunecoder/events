package com.company.events.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan(basePackages = {"com.company.events"})
@PropertySources({ @PropertySource("classpath:application.properties") })
public class EventsApplication {
    public static final String API_V1 = "/api/v1";
    public static void main(String[] args) {
    	// Application starts
        SpringApplication.run(EventsApplication.class, args);
    }
}
