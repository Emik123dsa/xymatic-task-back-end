package com.graphql.xymatic.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) {
       Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

       if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.OK.value()) {
                return ResponseEntity.ok().body("");
            } 

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404");
            }
       }

       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
