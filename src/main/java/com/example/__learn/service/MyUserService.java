package com.example.__learn.service;

import com.example.__learn.Entity.Users;
import com.example.__learn.repository.UsersRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {
    private UsersRepo usersRepo;

    public MyUserService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //check is this user valid or not?
        Users users = usersRepo.findByEmail(email)
                .orElseThrow(()-> new BadCredentialsException("This User Not Found."));

        //return the username & hash password
         User u = (User) User.builder()
                .username(users.getEmail())
                .password(users.getPassword())
                .authorities(users.getRole().toString())
                .build();
        return u;
    }
}
