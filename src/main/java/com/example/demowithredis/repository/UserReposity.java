package com.example.demowithredis.repository;

import com.example.demowithredis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposity extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

}
