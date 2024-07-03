package com.example.ticketing.service;

import com.example.ticketing.model.Resolution;
import com.example.ticketing.repository.ResolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResolutionService {

    @Autowired
    private ResolutionRepository resolutionRepository;

    public Resolution save(Resolution resolution) {
        return resolutionRepository.save(resolution);
    }

    public List<Resolution> findAll() {
        return resolutionRepository.findAll();
    }

    public void deleteById(Long id) {
        resolutionRepository.deleteById(id);
    }

    public Resolution findById(Long id) {
        return resolutionRepository.findById(id).orElseThrow(() -> new RuntimeException("Resolution not found with id " + id));
    }
}
