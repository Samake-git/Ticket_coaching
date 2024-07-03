package com.example.ticketing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resolutions")
public class Resolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ticket ticket;

    private String resolutionDetails;
    private LocalDateTime resolutionDate;

    public Resolution() {
        // Constructeur sans arguments
    }
}
