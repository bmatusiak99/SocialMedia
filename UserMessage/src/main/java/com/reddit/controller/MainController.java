package com.reddit.controller;

import com.reddit.domain.Message;
import com.reddit.domain.User;
import com.reddit.repository.MessageRepo;
import com.reddit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepo repository;
    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/user-messages/{user}")
    public String userMessages(@CookieValue(value = "username",defaultValue = "unknown") String username,@RequestParam(required = false, defaultValue = "") String filter,@PathVariable User user,@RequestParam(required = false) Message message, Map<String, Object>model){
        if(username.equals("unknown"))
        {
            return "redirect:http://localhost:9000/login";
        }
        else{
            User userFromDb = userRepo.findByUsername(username);
            Long currentUserId=userFromDb.getId();
            model.put("name",username);
            model.put("currentUserId",currentUserId);
            if(userFromDb.isAdmin())
                model.put("admin",true);
            Iterable<Message> messages;
            if(filter!=null && !filter.isEmpty())
                messages=repository.findByTagAndAuthor(filter,user);
            else
                messages=repository.findByAuthor(user);
            model.put("messages",messages);
            model.put("message",message);
            model.put("isCurrentUser",userFromDb.equals(user));
            return("userMessages");
        }
    }
    @PostMapping("/user-messages/{user}")
    public String updateMessage(@CookieValue(value = "username",defaultValue = "unknown") String username,@PathVariable Long user,@RequestParam("id") Message message,@RequestParam("text") String text,@RequestParam("tag") String tag,@RequestParam("file")MultipartFile file) throws IOException {
        User userFromDb = userRepo.findByUsername(username);
        if(message.getAuthor().equals(userFromDb)){
            if(!ObjectUtils.isEmpty(text)){
                message.setText(text);
            }
            if(!ObjectUtils.isEmpty(tag)){
                message.setTag(tag);
            }
            saveFile(file, message);
            repository.save(message);
        }
        return "redirect:http://localhost:9000/user-messages/"+user;
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
