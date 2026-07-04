package com.example.__learn.controller;

import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.LoginRequest;
import com.example.__learn.dto.Profile;
import com.example.__learn.dto.Register;
import com.example.__learn.service.AuthService;
import com.example.__learn.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<ApiResponse<Map<String, String>>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult result,
            HttpServletResponse response){

        if(result.hasErrors()){
            String errRes = result.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(errRes, null));
        }

        ApiResponse<Map<String, String>> loginRes = authService.login(loginRequest);

//        Cookie cookie = new Cookie("refreshToken", loginRes.getData().get("refreshToken"));
//        cookie.setPath("/");
//        cookie.setHttpOnly((true));
//        cookie.setSecure(false);
//        cookie.setS("None"),
//        cookie.setMaxAge(3600 *24 *7);
//        response.addCookie(cookie);
        ResponseCookie cookie = ResponseCookie.from("refreshToken",
                        loginRes.getData().get("refreshToken"))
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        Map<String, String> data = loginRes.getData();
        data.remove("refreshToken");
        return ResponseEntity.ok(new ApiResponse<>("Login Success.",data));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, String>>> add(@Valid @RequestBody Users user, BindingResult result){
        try {
            if(result.hasErrors()){
                String errRes = result.getFieldError().getDefaultMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(errRes, null));
            }

            if(usersService.UserEmailAlreadyExist(user)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>("This Email Already Exist There", null));
            }

            ApiResponse<Map<String, String>> res = usersService.addUser(user);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken) {
        Map<String, String> activeToken = authService.refresh(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>("okay", activeToken));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<Profile>> profile(){

        ApiResponse<Profile> response = authService.viewProfile();
        return ResponseEntity.ok(response);
    }
}
