package com.medical.api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private Integer userid;

    @Lob
    @Column(name = "question")
    private String question;

    @Lob
    @Column(name = "answer")
    private String answer;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}