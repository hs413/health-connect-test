package com.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiController {

    @PostMapping("/health")
    public void health(@RequestBody Object s) {
        System.out.println("a");
    }
}
