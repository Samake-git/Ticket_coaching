package com.example.ticketing.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class KnowledgeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String content;
}
