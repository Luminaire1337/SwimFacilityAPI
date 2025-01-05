package io.github.luminaire1337.swimfacilityapi.auth;

import io.github.luminaire1337.swimfacilityapi.models.user.User;
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
    @ApiResponse(description = "Login failed", responseCode = "403")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.authService.authenticate(authDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register with username and password")
    @ApiResponse(description = "Returns User object if successful", responseCode = "201")
    @ApiResponse(description = "Registration failed", responseCode = "403")
    public ResponseEntity<User> register(@RequestBody AuthDTO authDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(authDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate JWT token")
    @ApiResponse(description = "Returns User object if successful", responseCode = "200")
    @ApiResponse(description = "Token is invalid", responseCode = "403")
    public ResponseEntity<User> validate(@RequestBody ValidateTokenDTO validateTokenDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.authService.validateToken(validateTokenDTO.getToken()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
