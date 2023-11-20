package com.example.sweater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Пожалуйста, введите сообщение")
    @Length(max = 2048, message = "Вы ввели слишком длинное сообщение (больше 2 килобайт)")
    private String text;

    @Length(max = 255, message = "Вы ввели слишком длинный тег (больше 255 байт)")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "Автор отсутствует";
    }
}
