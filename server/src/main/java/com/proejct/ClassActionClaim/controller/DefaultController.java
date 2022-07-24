package com.proejct.ClassActionClaim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping
    public String mainPage() {
        return "Main Page";
    }
}
