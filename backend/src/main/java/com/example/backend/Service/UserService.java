package com.example.backend.Service;

import com.example.backend.Entity.User;
import com.example.backend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class UserService {

    @Autowired
    private UserRepo repo;

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public String addUser(User user){
        String str="";
        try{
            repo.save(user);
            str="Succesful";
        }catch (Exception e){
            str = "Unssesfull:"+e.getMessage();
        }
        return str;
    }
}
