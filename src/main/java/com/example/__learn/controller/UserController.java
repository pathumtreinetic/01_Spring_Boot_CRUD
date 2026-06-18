package com.example.__learn.controller;

import com.example.__learn.dto.ApiResponse;
import com.example.__learn.Entity.Users;
import com.example.__learn.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        List<Users> userList = usersService.getAll();
        if(userList.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("There is no users found.", null));

        return ResponseEntity.ok(new ApiResponse<>("Users Fetched Success.", userList));
    }
}
