package io.github.luminaire1337.swimfacilityapi;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    @Operation(summary = "Returns a Hello World message")
    public String index() {
        return "Hello, World!";
    }
}
