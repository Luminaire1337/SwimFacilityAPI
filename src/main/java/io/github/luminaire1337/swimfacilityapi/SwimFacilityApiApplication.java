package io.github.luminaire1337.swimfacilityapi;

import io.github.luminaire1337.swimfacilityapi.models.user.Role;
import io.github.luminaire1337.swimfacilityapi.models.user.User;
import io.github.luminaire1337.swimfacilityapi.models.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class SwimFacilityApiApplication {
    private static final Logger LOGGER = LogManager.getLogger(SwimFacilityApiApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(SwimFacilityApiApplication.class, args);
        LOGGER.info("Application started");
        LOGGER.debug("Debugging enabled");
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (args.length == 0) {
                return;
            }

            if (args[0].equals("create-user")) {
                if (args.length < 3) {
                    LOGGER.error("Usage: create-user <username> <password>");
                    return;
                }

                String username = args[1];
                String password = args[2];
                String role = args[3];

                if (userRepository.findByUsername(username).isPresent()) {
                    LOGGER.error("User already exists");
                    return;
                }

                User newUser = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .role(role.equals("admin") ? Role.ROLE_ADMIN : Role.ROLE_USER)
                        .build();

                userRepository.save(newUser);
                LOGGER.info("User {} created, role {}", newUser.getUsername(), newUser.getRole());
            }
        };
    }
}
