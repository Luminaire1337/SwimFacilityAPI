package io.github.luminaire1337.swimfacilityapi.auth;

import io.github.luminaire1337.swimfacilityapi.jwt.JwtService;
import io.github.luminaire1337.swimfacilityapi.models.user.Role;
import io.github.luminaire1337.swimfacilityapi.models.user.User;
import io.github.luminaire1337.swimfacilityapi.models.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public String validateToken(String token) {
        final String username = this.jwtService.validateToken(token);
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    public String authenticate(AuthDTO authDTO) throws RuntimeException {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
        );

        return this.jwtService.generateToken(authDTO.getUsername());
    }

    public String register(AuthDTO authDTO) throws RuntimeException {
        if (this.userRepository.findByUsername(authDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Save user to database
        User newUser = User.builder()
                .username(authDTO.getUsername())
                .password(this.passwordEncoder.encode(authDTO.getPassword()))
                .role(Role.USER)
                .build();

        this.userRepository.save(newUser);
        return this.jwtService.generateToken(newUser.getUsername());
    }
}
