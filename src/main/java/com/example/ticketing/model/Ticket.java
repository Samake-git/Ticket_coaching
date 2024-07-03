package com.example.ticketing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String description;
    private String category;
    private String priority;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @OneToMany
    private List<Assignment> assignments;

    @OneToMany
    private List<Resolution> resolutions;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public Ticket() {
        // Constructeur sans arguments
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
