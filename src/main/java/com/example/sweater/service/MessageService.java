package com.example.sweater.service;

import com.example.sweater.dto.MessageDto;
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

    public Page<MessageDto> messageList(String filter, Pageable pageable, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Page<MessageDto> findAll(Pageable pageable, User user) {
        return messageRepository.findAll(pageable, user);
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User author, User user) {
        return messageRepository.findByUser(pageable, author, user);
    }
}
