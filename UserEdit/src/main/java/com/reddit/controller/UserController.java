package com.reddit.controller;

import com.reddit.domain.Role;
import com.reddit.domain.User;
import com.reddit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Map<String, Object> model){
        model.put("users",userRepo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Map<String, Object> model){
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }
    @PostMapping
    public String userSave(@RequestParam String username, @RequestParam Map<String, String> form, @RequestParam("userId") User user){
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()); //pobierz z klasy role jakie pelni uzytkownik
        user.getRoles().clear();       //czysci w formularze role jakie posiada uzytkownik
        for(String key : form.keySet()){  //przejezdza po checkboxach i je sprawdza
            if(roles.contains(key)){ //jezeli jest nacisniety checkbox
                user.getRoles().add(Role.valueOf(key)); //to do roli uzytkownika dodaje wartosc z tego checkboxa
            }
        }

        userRepo.save(user);
        return "redirect:http://localhost:9000/user";
    }

}
