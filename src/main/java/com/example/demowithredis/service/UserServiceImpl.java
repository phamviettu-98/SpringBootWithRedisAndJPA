package com.example.demowithredis.service;

import com.example.demowithredis.model.User;
import com.example.demowithredis.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements  UserService{
    @Autowired
    private UserDAO userDAO;
    @Override
    public boolean saveUser(User user) {

        return userDAO.saveUser(user);
    }

    @Override
    public boolean updateUser(Long id, User user) {
        return userDAO.updateUser(id, user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userDAO.findUserById(id);
    }



    @Override
    public boolean deleteUser(Long id,  User user) {
        return userDAO.deleteUser(id, user);
    }

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }
}
