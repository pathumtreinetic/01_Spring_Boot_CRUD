package com.example.__learn.service;

import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.LoginRequest;
import com.example.__learn.dto.LoginRes;
import com.example.__learn.dto.Profile;
import com.example.__learn.repository.UsersRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private MyUserService myUserService;
    private UsersRepo usersRepo;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, MyUserService myUserService, UsersRepo usersRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserService = myUserService;
        this.usersRepo = usersRepo;
    }

    public ApiResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .get()
                    .getAuthority();

            String activeToken = jwtService.generateToken(userDetails.getUsername(), role);
            String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername(), role);
            Map<String, String> data = new HashMap<>();
            data.put("activeToken", activeToken);
            data.put("refreshToken", refreshToken);

            return new ApiResponse<>("Login Success.", data);
        } catch (BadCredentialsException nf){
            return new ApiResponse<>("This user not found..", null);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> refresh(String refreshToken) {
        try {
            String userEmail = jwtService.extractUserEmail(refreshToken);
            UserDetails userDetails = myUserService.loadUserByUsername(userEmail);

            String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .get()
                    .getAuthority();

            Map<String, String> activeRes = new HashMap<>();
            if(jwtService.validateToken(refreshToken, userDetails)){
                String activeToken =  jwtService.generateRefreshToken(userEmail,role );
                activeRes.put("activeToken", activeToken);
            }

            return activeRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<Profile> viewProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        Users user = usersRepo.findByEmail(authentication.getName())
                .orElseThrow(()-> new BadCredentialsException("This User Not Found."));
        Profile profile = new Profile();
        profile.setEmail(user.getEmail());
        profile.setRole(user.getRole());
        profile.setUname(user.getName());

        return new ApiResponse<Profile>("Profile fetched.",profile);
    }
}
