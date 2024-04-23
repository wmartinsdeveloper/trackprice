package com.agrotech.usersecurity.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ManagerController {

    // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin")
    public String getMethodName() {
        return "Private Area !!!";
    }

}
