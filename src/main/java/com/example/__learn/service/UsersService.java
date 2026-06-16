package com.example.__learn.service;

import com.example.__learn.dto.ApiResponse;
import com.example.__learn.Entity.Users;
import com.example.__learn.repository.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private UsersRepo usersRepo;

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
        Users res = usersRepo.save(user);
        return new ApiResponse<>("Successfully add user.", res);
    }

    public List<Users> getAll() {
        List<Users> users = usersRepo.findAll();
        return users;
    }
}
