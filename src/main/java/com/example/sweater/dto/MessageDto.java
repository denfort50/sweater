package com.example.sweater.dto;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.util.MessageHelper;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = {"text", "tag", "filename"})
public class MessageDto {

    final private Long id;
    final private String text;
    final private String tag;
    final private User author;
    final private String filename;
    final private Long likes;
    final private Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }
}
