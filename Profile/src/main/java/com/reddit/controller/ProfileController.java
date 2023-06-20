package com.reddit.controller;

import com.reddit.domain.User;
import com.reddit.repository.UserRepo;
import com.reddit.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class ProfileController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/profile")
    public String getProfile(Map<String, Object> model,@CookieValue(name = "username", defaultValue = "unknown") String username){
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb.isAdmin())
            model.put("admin",true);
        model.put("name",username);
        model.put("username",userFromDb.getUsername());
        model.put("password",userFromDb.getPassword());
        model.put("email",userFromDb.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Map<String, Object> model,@CookieValue(name = "username", defaultValue = "unknown") String username, @RequestParam String password,@RequestParam String email){
        User userFromDb = userRepo.findByUsername(username);
        String userEmail= userFromDb.getEmail();
        boolean isEmailChanged=(email != null && !email.equals(userEmail)) || (userEmail!=null && !userEmail.equals(email));
        if(isEmailChanged)
        {
            userFromDb.setEmail(email);
            if(!ObjectUtils.isEmpty(email)){
                userFromDb.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if(!ObjectUtils.isEmpty(password)){
            userFromDb.setPassword(password);
        }
        userRepo.save(userFromDb);

        if(isEmailChanged)
        {
            if(!ObjectUtils.isEmpty(userFromDb.getEmail()))
            {
                String message = String.format("Hello, %s! \n"+"Welcome to Reddit. Please, visit next link: http://localhost:9000/activate/%s",
                        userFromDb.getUsername(),
                        userFromDb.getActivationCode());
                mailSender.send(userFromDb.getEmail(), "Activation code", message);
            }
        }
        if(userFromDb.isAdmin())
        {
            model.put("admin",true);
        }

        model.put("name",username);

        model.put("username",userFromDb.getUsername());
        model.put("password",userFromDb.getPassword());
        model.put("email",userFromDb.getEmail());
        return "profile";
    }
}
