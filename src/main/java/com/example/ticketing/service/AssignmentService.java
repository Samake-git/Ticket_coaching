package com.example.ticketing.service;

import com.example.ticketing.model.Assignment;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public void deleteById(Long id) {
        assignmentRepository.deleteById(id);
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found with id " + id));
    }


}
