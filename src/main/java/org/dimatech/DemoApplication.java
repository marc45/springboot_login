package org.dimatech;

import org.dimatech.model.FileStorageProperties;
import org.dimatech.model.Role;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.dimatech.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DemoApplication {

    public static void main(String[] args) {
        run(DemoApplication.class, args);
    }
    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
        };

    }
}

