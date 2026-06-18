package com.example.__learn.controller;

import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.LoginRequest;
import com.example.__learn.dto.Register;
import com.example.__learn.service.AuthService;
import com.example.__learn.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private UsersService usersService;

    public AuthController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        ApiResponse loginRes = authService.login(loginRequest);
        return ResponseEntity.ok(loginRes);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> add(@RequestBody Users user){
        try {
            if(usersService.UserNameAlreadyExist(user)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>("This Username Already Exist There", null));
            }

            ApiResponse res = usersService.addUser(user);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
