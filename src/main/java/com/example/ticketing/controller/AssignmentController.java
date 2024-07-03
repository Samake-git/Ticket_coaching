package com.example.ticketing.controller;

import com.example.ticketing.model.Assignment;
import com.example.ticketing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@Api
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/assign")
    public Assignment assignTicket(@RequestParam Long ticketId, @RequestParam String assignedTo) {
        return ticketService.assignTicket(ticketId, assignedTo);
    }

    @GetMapping("/all")
    public List<Assignment> getAllAssignments() {
        return ticketService.findAllAssignments();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        ticketService.deleteAssignmentById(id);
    }
}
