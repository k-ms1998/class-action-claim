package com.proejct.ClassActionClaim.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DefaultController {

    @GetMapping
    public String mainPage() {
        log.info("[GET] Main Page");
        return "Main Page";
    }

    @PostMapping("/linkTest")
    public void linkTest() {
        log.info("[POST] Client-Server Linked");
    }
}
