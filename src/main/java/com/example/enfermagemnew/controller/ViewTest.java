package com.example.enfermagemnew.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enfermagem")
public class ViewTest {

    @GetMapping("test")
    public String hello(){
        return "Hello World";
    }


}
