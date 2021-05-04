package com.example.demowithredis.service;

import com.example.demowithredis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean saveUser(User user);
    boolean updateUser(Long id, User user);
    Optional<User> findUserById(Long id);
    boolean deleteUser(Long id, User user);
    List<User> getAllUser();
}
