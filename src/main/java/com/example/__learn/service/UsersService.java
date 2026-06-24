package com.example.__learn.service;

import com.example.__learn.dto.ApiResponse;
import com.example.__learn.Entity.Users;
import com.example.__learn.dto.StudentResponse;
import com.example.__learn.repository.UsersRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private UsersRepo usersRepo;
    private  final BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }


    public ApiResponse addUser(Users user){
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        Users res = usersRepo.save(user);
        return new ApiResponse<>("Successfully add user.", res);
    }

    public List<StudentResponse> getAll() {
        List<Users> users = usersRepo.findAll();
        List<StudentResponse> students = new ArrayList<>();
        for(Users users1 : users){
            StudentResponse temp = new StudentResponse();
            temp.setId(users1.getId());
            temp.setName(users1.getName());
            temp.setEmail(users1.getEmail());
            temp.setCity(users1.getCity());
            temp.setRole(users1.getRole());
            students.add(temp);
        }
        return students;
    }

    public boolean UserEmailAlreadyExist(Users user) {
        if(usersRepo.existsByName(user.getEmail())){
            return true;
        }
        return false;
    }

    public StudentResponse getStudent(long id) {
        Users user = usersRepo.findById(id)
                .orElseThrow(()-> new BadCredentialsException("This User Not Found."));
        StudentResponse response = new StudentResponse();
        response.setRole(user.getRole());
        response.setId(user.getId());
        response.setCity(user.getCity());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return response;

    }
}
