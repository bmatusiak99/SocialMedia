package com.reddit.controller;

import com.reddit.domain.Message;
import com.reddit.domain.User;
import com.reddit.repository.MessageRepo;
import com.reddit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private MessageRepo repository;
    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(@CookieValue(name = "username", defaultValue = "unknown") String username,Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(username);
        if(userFromDb != null)
        {
            if(userFromDb.isAdmin())
                model.put("admin",true);
            Long currentUserId=userFromDb.getId();
            model.put("currentUserId",currentUserId);
        }
        model.put("name",username);
        return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, @CookieValue(name = "username",defaultValue = "unknown") String username, Map<String, Object>model)
    {
        if(username.equals("unknown"))
        {
            return "redirect:http://localhost:9000/login";
        }
        else
        {
            User userFromDb = userRepo.findByUsername(username);
            Long currentUserId=userFromDb.getId();
            if(userFromDb.isAdmin())
                model.put("admin",true);
            model.put("name",username);
            model.put("currentUserId",currentUserId);
            Iterable<Message> messages = repository.findAll();
            if(filter!=null && !filter.isEmpty())
                messages=repository.findByTag(filter);
            else
                messages=repository.findAll();
            model.put("messages",messages);
            model.put("filter", filter);
            return "main";
        }
    }
    @PostMapping("/main")
    public String add(@CookieValue(value = "username",defaultValue = "unknown") String username, @RequestParam String text, @RequestParam String tag, Map<String, Object>model, @RequestParam("file")MultipartFile file) throws IOException {
        if(username.equals("unknown"))
        {
            return "redirect:http://localhost:9000/login";
        }
        User user = userRepo.findByUsername(username);
        Message message = new Message(text, tag, user);
        saveFile(file, message);
        repository.save(message);

        return "redirect:http://localhost:9000/main";
    }

    private void saveFile(MultipartFile file, Message message) throws IOException {
        if(file !=null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
            {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }


}
