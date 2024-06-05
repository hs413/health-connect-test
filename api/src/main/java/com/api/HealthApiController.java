package com.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class HealthApiController {

    @PostMapping("/health")
    public void health(@RequestBody LinkedHashMap s) {
        System.out.println(s.get("count"));
        System.out.println(s.get("timestamp"));
    }
}
