package com.example.backend.Controller;

import com.example.backend.Entity.User;

import com.example.backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    //create user
    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return  userService.createUser(user);
    }

    @GetMapping("/all")
    //get all users
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }

    //get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id){
        return  userService.getUserById(id);
    }

    //delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable UUID id){
          userService.deleteUser(id);
          return  "User deleted successfully";
    }
}
