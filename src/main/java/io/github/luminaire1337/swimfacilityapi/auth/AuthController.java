package io.github.luminaire1337.swimfacilityapi.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login with username and password")
    @ApiResponse(description = "Returns JWT token if login was successful", responseCode = "200")
    @ApiResponse(description = "Login failed", responseCode = "401")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        try {
            final String token = this.authService.authenticate(authDTO);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register with username and password")
    @ApiResponse(description = "Returns JWT token if registration was successful", responseCode = "201")
    @ApiResponse(description = "Registration failed", responseCode = "400")
    public ResponseEntity<String> register(@RequestBody AuthDTO authDTO) {
        try {
            final String token = this.authService.register(authDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate JWT token")
    @ApiResponse(description = "Returns username if token is valid", responseCode = "200")
    @ApiResponse(description = "Token is invalid", responseCode = "401")
    public ResponseEntity<String> validate(@RequestBody ValidateTokenDTO validateTokenDTO) {
        try {
            final String username = this.authService.validateToken(validateTokenDTO.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(username);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
