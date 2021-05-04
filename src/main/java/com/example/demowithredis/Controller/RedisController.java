package com.example.demowithredis.Controller;

import com.example.demowithredis.model.User;
import com.example.demowithredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RedisController {
    @Autowired
    private UserService userService;
    @PostMapping("/users")
    public ResponseEntity<?> ShowUser(@RequestBody User user){
        if ( userService.saveUser(user) ){
            return  ResponseEntity.ok("Pass");
        }
        return  ResponseEntity.ok("Error");

    }
    @GetMapping("/users")
    public  ResponseEntity<?> showAllUser(){
        List<User> users = userService.getAllUser();
        if ( users.isEmpty()){
            return  ResponseEntity.ok("Null");
        }
        return  ResponseEntity.ok(users);
    }
    @PutMapping("/users/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        boolean result = userService.updateUser(id, user);
        if ( result){
            return  ResponseEntity.ok("Ok");
        }
        return ResponseEntity.ok("Error");

    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, @RequestBody User user){
        boolean result = userService.deleteUser(id, user);
        if( result){
            return  ResponseEntity.ok("Ok");

        }
        return  ResponseEntity.ok("Error");
    }
}
