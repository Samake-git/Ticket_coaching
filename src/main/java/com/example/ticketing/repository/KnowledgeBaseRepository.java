package com.example.ticketing.repository;

import com.example.ticketing.model.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {
    // Custom query methods if needed
}