package com.example.sweater.service;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Page<Message> messageList(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter, pageable);
        } else {
            return messageRepository.findAll(pageable);
        }
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Page<Message> messageListForUser(Pageable pageable, User author) {
        return messageRepository.findByUser(pageable, author);
    }
}
