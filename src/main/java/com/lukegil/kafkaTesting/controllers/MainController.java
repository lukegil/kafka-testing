package com.lukegil.kafkaTesting.controllers;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@ComponentScan({ "com.lukegil.kafkaTesting" })
@RestController
@EnableCaching
public class MainController {

    private static final Logger LOG = Logger.getLogger(MainController.class);

    public static void main(final String[] args) {
        SpringApplication.run(MainController.class, args);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping() {
        return "success";
    }

    @ExceptionHandler(Throwable.class)
    void handleAllOthersExceptions(final Throwable e, final HttpServletRequest req, final HttpServletResponse response)
            throws IOException {
        LOG.error(e.getMessage(), e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
