package com.reddit.repository;

import com.reddit.domain.Message;
import com.reddit.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long>{

    List<Message> findByTag(String tag);
    List<Message> findByTagAndAuthor(String tag, User author);
    List<Message> findByAuthor(User author);
}
