package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Usersecurity {

    @GetMapping("/")
    public String getMethodName() {
        return "Wellington";
    }

}
