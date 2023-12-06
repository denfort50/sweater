package com.example.sweater.util;

import com.example.sweater.model.User;

public abstract class MessageHelper {

    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "Автор отсутствует";
    }
}
