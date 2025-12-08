package com.example.backend.Service;
import com.example.backend.Entity.User;
import com.example.backend.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepo userRepo;

    //user creation
    public User createUser(User user){
        if(userRepo.existsByEmail(user.getEmail())){
            throw  new RuntimeException("Email already exists");
        }
        if(userRepo.existsByPhoneNo(user.getPhoneNo())){
            throw  new RuntimeException("Email already exists");
        }
        return userRepo.save(user);
    }

    //fetching all users
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    //fetching user by ID
    public  User getUserById(UUID id){
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("user not found with ID:"+id));

    }

    //delete user
    public void deleteUser(UUID id){
        userRepo.deleteById(id);
    }
}