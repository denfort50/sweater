package com.example.sweater.repository;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Page<Message> findByTag(String tag, Pageable pageable);

    Page<Message> findAll(Pageable pageable);

    Page<Message> findAllByAuthor(User user, Pageable pageable);
}
