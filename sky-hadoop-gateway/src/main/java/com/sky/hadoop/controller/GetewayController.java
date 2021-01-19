package com.sky.hadoop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geteway")
public class GetewayController {

    @GetMapping("/hello")
    public String hello() {
        return "spring cloud geteway hello-world!!!";
    }

}

