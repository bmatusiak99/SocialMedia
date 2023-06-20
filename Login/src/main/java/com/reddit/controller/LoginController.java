package com.reddit.controller;

import com.reddit.domain.User;
import com.reddit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String login(@CookieValue(name = "username", defaultValue = "unknown") String username, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb != null)
        {
            if(userFromDb.isAdmin())
                model.put("admin",true);
        }
        model.put("name",username);
        return "login";
    }
    @GetMapping("/login/{message1}")
    public String loginSpec(@CookieValue(name = "username", defaultValue = "unknown") String username, @PathVariable String message1, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb != null)
        {
            if(userFromDb.isAdmin())
                model.put("admin",true);
        }
        if(message1.equals("false"))
            model.put("message","Activation code is not found!");
        else
            model.put("message2","User successfully activated");
        model.put("name",username);

        return "login";
    }
    @PostMapping("/login")
    public String check(@RequestParam String username, @RequestParam String password,@CookieValue(name = "username", defaultValue = "unknown") String name, HttpServletResponse response, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb == null || !userFromDb.getPassword().equals(password))
        {
            model.put("name",name);
            model.put("message","Podany użytkownik nie istnieje");
            return "login";
        }
        else
        {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return "redirect:http://localhost:9000/";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie=new Cookie ("username", null);
        cookie.setMaxAge(0);
        response.addCookie (cookie);
        return "redirect:http://localhost:9000/";
    }
}