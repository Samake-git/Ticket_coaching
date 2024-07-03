package com.example.ticketing.controller;

import com.example.ticketing.model.Ticket;
import com.example.ticketing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/assign/{id}")
    public Ticket assignTicket(@PathVariable Long id, @RequestParam String assignedTo) {
        return ticketService.assignTicket(id, assignedTo).getTicket();
    }

    @PutMapping("/resolve/{id}")
    public Ticket resolveTicket(@PathVariable Long id, @RequestParam String resolutionDetails, @RequestParam String resolvedBy) {
        return ticketService.resolveTicket(id, resolutionDetails, resolvedBy).getTicket();
    }

    @PostMapping("/create")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }

    @GetMapping("/afficher")
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @PutMapping("/update/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticketDetails) {
        return ticketService.update(id, ticketDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
    }
}
