package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AddUserRequest;
import com.example.healthmanagement.dtos.RegisterRequest;
import com.example.healthmanagement.dtos.UserResponse;
import com.example.healthmanagement.exception.UserAlreadyExistException;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Transactional
    public String deleteUser(String contactPhone) {
        User user = userRepository.findByContactPhone(contactPhone);
        if (user == null) {
            throw new RuntimeException("User not found with phone: " + contactPhone);
        }
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Transactional
    public String addUser(AddUserRequest request) {
        if (userRepository.findByContactPhone(request.getContactPhone()) != null) {
            throw new UserAlreadyExistException("Phone number already in use");
        }
        User user = modelMapper.map(request,User.class);
        user.setInsuranceStatus("ACTIVE");
        String password=user.getLastName().toUpperCase();
        user.setPasswordHash(passwordEncoder.encode(password));

        //save user
        userRepository.save(user);

        return "User added successfully";
    }

    // manual mapping — no ModelMapper, no lazy load triggers, exactly 1 DB query
    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDob(),
                user.getSex(),
                user.getContactPhone(),
                user.getInsuranceStatus(),
                user.getRole()
        );
    }
}