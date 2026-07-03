package com.example.healthmanagement;

import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.model.enums.Sex;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedAdmin implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        String adminPhone = "123456789";

        if (userRepository.findByContactPhone(adminPhone) == null) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setContactPhone(adminPhone);
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMINISTRATOR);
            admin.setSex(Sex.MALE);
            admin.setDob(LocalDate.of(2002, 3, 18));
            admin.setInsuranceStatus("Active");

            userRepository.save(admin);
            log.info("✅ Default admin seeded successfully");
        } else {
            log.info("ℹ️ Default admin already exists — skipping seed");
        }
    }
}