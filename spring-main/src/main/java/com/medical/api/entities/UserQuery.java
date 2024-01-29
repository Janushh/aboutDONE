package com.medical.api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class UserQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Lob
    @Column(name = "question")
    private String question;

    @Lob
    @Column(name = "answer")
    private String answer;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;



    public void setUsername(String username) {
        this.username = username;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }



}
