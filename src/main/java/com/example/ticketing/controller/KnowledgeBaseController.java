package com.example.ticketing.controller;

import com.example.ticketing.model.KnowledgeBase;
import com.example.ticketing.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@Api
@RestController
@RequestMapping("/api/knowledgebase")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @PostMapping(path = "/repondre")
    public ResponseEntity<KnowledgeBase> createKnowledgeBase(@RequestBody KnowledgeBase knowledgeBase) {
        KnowledgeBase createdKnowledgeBase = knowledgeBaseService.createKnowledgeBase(knowledgeBase);
        return ResponseEntity.ok(createdKnowledgeBase);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<KnowledgeBase> updateKnowledgeBase(@PathVariable Long id, @RequestBody KnowledgeBase knowledgeBase) {
        knowledgeBase.setId(id);
        KnowledgeBase updatedKnowledgeBase = knowledgeBaseService.updateKnowledgeBase(knowledgeBase);
        return ResponseEntity.ok(updatedKnowledgeBase);
    }

    @GetMapping(path = "/afficher")
    public ResponseEntity<List<KnowledgeBase>> getAllKnowledgeBaseEntries() {
        List<KnowledgeBase> knowledgeBaseEntries = knowledgeBaseService.getAllKnowledgeBaseEntries();
        return ResponseEntity.ok(knowledgeBaseEntries);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<KnowledgeBase> getKnowledgeBaseById(@PathVariable Long id) {
        KnowledgeBase knowledgeBase = knowledgeBaseService.getKnowledgeBaseById(id);
        return ResponseEntity.ok(knowledgeBase);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteKnowledgeBase(@PathVariable Long id) {
        knowledgeBaseService.deleteKnowledgeBase(id);
        return ResponseEntity.noContent().build();
    }
}