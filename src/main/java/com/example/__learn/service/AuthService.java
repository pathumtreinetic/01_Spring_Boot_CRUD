package com.example.__learn.service;

import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.LoginRequest;
import com.example.__learn.dto.LoginRes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ApiResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUname(), loginRequest.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .get()
                    .getAuthority();

            String token = jwtService.generateToken(userDetails.getUsername(), role);
            Map<String, String> data = new HashMap<>();
            data.put("token", token);

            return new ApiResponse<>("Login Success.", data);
        } catch (BadCredentialsException nf){
            return new ApiResponse<>("This user not found..", null);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
