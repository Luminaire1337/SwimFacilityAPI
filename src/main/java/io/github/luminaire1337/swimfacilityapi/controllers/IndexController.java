package io.github.luminaire1337.swimfacilityapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Operation(summary = "Returns a Hello World message")
    @GetMapping("/")
    public String index() {
        return "Hello, World!";
    }
}
