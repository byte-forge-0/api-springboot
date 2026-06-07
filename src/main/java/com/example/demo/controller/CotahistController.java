package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CotahistController {
    @GetMapping("/hello")
    public String cotahist(){
        return "hello,world";
    }
}
