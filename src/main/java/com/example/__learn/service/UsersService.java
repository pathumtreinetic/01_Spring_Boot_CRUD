package com.example.__learn.service;

import com.example.__learn.dto.ApiResponse;
import com.example.__learn.Entity.Users;
import com.example.__learn.repository.UsersRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private UsersRepo usersRepo;
    private  final BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public Boolean UserNameAlreadyExist(Users user){
        if(usersRepo.existsByName(user.getName())){
            return true;
        }
        return false;
    }

    public ApiResponse addUser(Users user){
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        Users res = usersRepo.save(user);
        return new ApiResponse<>("Successfully add user.", res);
    }

    public List<Users> getAll() {
        List<Users> users = usersRepo.findAll();
        return users;
    }
}
