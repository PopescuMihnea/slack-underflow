package com.slackunderflow.slackunderflow.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caca")
@CrossOrigin("*")
public class Caca {

    @GetMapping
    public String getCaca(Authentication authentication) {
        System.out.println("credentials " + authentication.getCredentials());
        System.out.println("username " + authentication.getName());
        System.out.println("authorities" + authentication.getAuthorities());
        return "caca";
    }

    @GetMapping("/admin")
    public String getAdminCaca() {
        return "admin facu caca";
    }
}
