package com.example.ticketing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne

    private Ticket ticket;

    private String assignedTo;
    private LocalDateTime assignedDate;

    public Assignment() {
        // Constructeur sans arguments
    }
}
