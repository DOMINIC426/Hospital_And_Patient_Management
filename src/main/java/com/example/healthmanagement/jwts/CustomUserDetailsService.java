package com.example.healthmanagement.jwts;

import com.example.healthmanagement.model.User;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        User user = userRepository.findByContactPhone(phone);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
