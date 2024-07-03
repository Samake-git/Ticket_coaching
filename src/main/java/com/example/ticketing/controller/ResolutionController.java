package com.example.ticketing.controller;

import com.example.ticketing.model.Resolution;
import com.example.ticketing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/resolutions")
public class ResolutionController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/resolve")
    public Resolution resolveTicket(@RequestParam Long ticketId, @RequestParam String resolutionDetails, @RequestParam String resolvedBy) {
        return ticketService.resolveTicket(ticketId, resolutionDetails, resolvedBy);
    }

    @GetMapping("/all")
    public List<Resolution> getAllResolutions() {
        return ticketService.findAllResolutions();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteResolution(@PathVariable Long id) {
        ticketService.deleteResolutionById(id);
    }
}
