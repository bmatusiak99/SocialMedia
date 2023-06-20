package com.reddit.controller;

import com.reddit.domain.Role;
import com.reddit.domain.User;
import com.reddit.repository.UserRepo;
import com.reddit.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @GetMapping("/registration")
    public String registration(@CookieValue(name = "username", defaultValue = "unknown") String username, Map<String, Object> model)
    {
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb != null)
        {
            if(userFromDb.isAdmin())
                model.put("admin",true);
        }
        model.put("name",username);
        return "registration";
    }
    //User user
    @PostMapping("/registration")
    public String addUser(@CookieValue(name = "username", defaultValue = "unknown") String name,@RequestParam String username, @RequestParam String password, @RequestParam String password2,@RequestParam String email, Map<String, Object> model){
        if(!password.equals(password2))
        {
            model.put("name",name);
            model.put("message","Hasła muszą się zdazdać");
            return "registration";
        }
        User user= new User(username,password,email);
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb != null)
        {
            model.put("name",name);
            model.put("message","Podany login już istnieje!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        if(!ObjectUtils.isEmpty(user.getEmail()))
        {
            String message = String.format("Hello, %s! \n"+"Welcome to Reddit. Please, visit next link: http://localhost:9000/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return "redirect:http://localhost:9000/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Map<String, Object> model, @PathVariable String code){
        User user = userRepo.findByActivationCode(code);
        if(user == null)
        {

            return "redirect:http://localhost:9000/login/false";
        }
        else
        {
            user.setActivationCode(null);
            userRepo.save(user);
            return "redirect:http://localhost:9000/login/true";
        }

    }
}