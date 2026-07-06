package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AuthenticationResponse;
import com.example.healthmanagement.dtos.LoginRequest;
import com.example.healthmanagement.dtos.RegisterRequest;
import com.example.healthmanagement.dtos.RegisterResponse;
import com.example.healthmanagement.exception.UserAlreadyExistException;
import com.example.healthmanagement.jwts.JwtService;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByContactPhone(request.getContactPhone()) != null) {
            throw new UserAlreadyExistException("Phone number already in use");
        }

        User user = modelMapper.map(request, User.class);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setInsuranceStatus("Active");
        user.setRole(Role.PATIENT);
        User savedUser = userRepository.save(user);

        //adding the user Role in token
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",savedUser.getRole());
        claims.put("name",savedUser.getFirstName());

        String token = jwtService.generateToken(claims,savedUser);
        return new RegisterResponse(token);
    }


    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getContactPhone(), request.getPassword())
        );

        User user = userRepository.findByContactPhone(request.getContactPhone());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        //include the role in token
        Map<String,Object> claims= new HashMap<>();
        claims.put("role",user.getRole().name());

        String token = jwtService.generateToken(claims,user);
        return new AuthenticationResponse(token);
    }
}
