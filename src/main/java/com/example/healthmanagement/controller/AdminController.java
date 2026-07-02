package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.RegisterRequest;
import com.example.healthmanagement.dtos.UserResponse;
import com.example.healthmanagement.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/delete/user/{contact}")
    public ResponseEntity<String> deleteUser(@PathVariable String contact) {
        return ResponseEntity.ok(adminService.deleteUser(contact));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(adminService.addUser(request));
    }
}