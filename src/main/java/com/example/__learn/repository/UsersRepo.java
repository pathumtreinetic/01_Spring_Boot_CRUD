package com.example.__learn.repository;

import com.example.__learn.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    @Override
    Optional<Users> findById(Long aLong);

    boolean existsByName(String name);
}
