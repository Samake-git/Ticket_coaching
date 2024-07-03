package com.example.ticketing.service;

import com.example.ticketing.model.KnowledgeBase;
import com.example.ticketing.repository.KnowledgeBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeBaseService {

    @Autowired
    private KnowledgeBaseRepository knowledgeBaseRepository;

    public KnowledgeBase createKnowledgeBase(KnowledgeBase knowledgeBase) {
        return knowledgeBaseRepository.save(knowledgeBase);
    }

    public KnowledgeBase updateKnowledgeBase(KnowledgeBase knowledgeBase) {
        return knowledgeBaseRepository.save(knowledgeBase);
    }

    public List<KnowledgeBase> getAllKnowledgeBaseEntries() {
        return knowledgeBaseRepository.findAll();
    }

    public KnowledgeBase getKnowledgeBaseById(Long id) {
        return knowledgeBaseRepository.findById(id).orElse(null);
    }

    public void deleteKnowledgeBase(Long id) {
        knowledgeBaseRepository.deleteById(id);
    }
}