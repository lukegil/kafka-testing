package com.lukegil.kafkaTesting.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan({ "com.lukegil.kafkaTesting" })
@RestController
@EnableCaching
public class MainController {

    private static final Logger LOG = Logger.getLogger(MainController.class.getName());

    public static void main(final String[] args) {
        SpringApplication.run(MainController.class, args);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping() {
        return "success";
    }
}
