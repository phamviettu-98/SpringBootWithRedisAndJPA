package com.example.demowithredis.repository;

import com.example.demowithredis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    boolean saveUser(User user);
    Optional<User> findUserById(Long id);
    Boolean updateUser(Long id,User user);
    List<User> getAllUser();
    boolean deleteUser(Long id, User user);

}
