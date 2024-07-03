package com.example.ticketing.service;

import com.example.ticketing.Enum.RoleType;
import com.example.ticketing.model.Assignment;
import com.example.ticketing.model.Resolution;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.model.User;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ResolutionService resolutionService;

    public Ticket save(Ticket ticket) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String currentUsername = authentication.getName();
            System.out.println(currentUsername + " is logged in");
            User creator = userRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + currentUsername));

            ticket.setCreator(creator);
        } else {
            throw new RuntimeException("No authenticated user found");
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        try {
            notifyUsers(savedTicket, "New Ticket Created", "A new ticket has been created:\n\n" + savedTicket);
        } catch (Exception e) {
            System.err.println("Failed to send notification email: " + e.getMessage());
        }
        return savedTicket;
    }


    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public Ticket update(Long id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        ticket.setTitle(ticketDetails.getTitle());
        ticket.setDescription(ticketDetails.getDescription());
        ticket.setCategory(ticketDetails.getCategory());
        ticket.setPriority(ticketDetails.getPriority());
        ticket.setStatus(ticketDetails.getStatus());
        ticket.setUpdatedDate(LocalDateTime.now());

        Ticket updatedTicket = ticketRepository.save(ticket);
        try {
            notifyUsers(updatedTicket, "Ticket Updated", "A ticket has been updated:\n\n" + updatedTicket);
        } catch (Exception e) {
            // Log the error
            System.err.println("Failed to send notification email: " + e.getMessage());
        }
        return updatedTicket;
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
    }

    public Assignment assignTicket(Long ticketId, String assignedTo) {
        Ticket ticket = findById(ticketId);
        Optional<User> user = userRepository.findByEmail(assignedTo);

        if (user.isPresent() && user.get().getRole() == RoleType.Coach) {
            Assignment assignment = new Assignment();
            assignment.setTicket(ticket);
            assignment.setAssignedTo(assignedTo);
            assignment.setAssignedDate(LocalDateTime.now());
            Assignment savedAssignment = assignmentService.save(assignment);

            notifyUsers(ticket, "Ticket Assigned", "A ticket has been assigned to: " + assignedTo + "\n\n" + ticket);

            // Notify the assignee and the creator
            notifyUser(user.get().getEmail(), "You have been assigned a ticket", "Ticket details: " + ticket);
            notifyUser(ticket.getCreator().getEmail(), "Your ticket has been assigned", "Ticket details: " + ticket);

            return savedAssignment;
        } else {
            notifyUser(ticket.getCreator().getEmail(), "Assignment Failed", "The user you tried to assign is not a valid instructor.");
            throw new RuntimeException("The user is not a valid instructor");
        }
    }

    public Resolution resolveTicket(Long ticketId, String resolutionDetails, String resolvedBy) {
        Ticket ticket = findById(ticketId);
        Optional<User> resolver = userRepository.findByEmail(resolvedBy);

        if (resolver.isPresent() && resolver.get().getRole() == RoleType.Coach) {
            Resolution resolution = new Resolution();
            resolution.setTicket(ticket);
            resolution.setResolutionDetails(resolutionDetails);
            resolution.setResolutionDate(LocalDateTime.now());
            Resolution savedResolution = resolutionService.save(resolution);

            // Notify users about the ticket resolution
            notifyUsers(ticket, "Ticket Resolved", "A ticket has been resolved by: " + resolvedBy + "\n\n" + ticket);

            // Notify the creator of the ticket
            notifyUser(ticket.getCreator().getEmail(), "Your ticket has been resolved", "Ticket details: " + ticket + "\nResolution details: " + resolutionDetails);

            return savedResolution;
        } else {
            throw new RuntimeException("The user is not a valid instructor");
        }
    }


    public void deleteAssignmentById(Long id) {
        assignmentService.deleteById(id);
    }

    public List<Assignment> findAllAssignments() {
        return assignmentService.findAll();
    }

    public void deleteResolutionById(Long id) {
        resolutionService.deleteById(id);
    }

    public List<Resolution> findAllResolutions() {
        return resolutionService.findAll();
    }

    private void notifyUsers(Ticket ticket, String subject, String body) {
        // Suppose you have a list of user emails
        List<String> userEmails = List.of("exemple@gmail.com", "test@gmail.com");

        for (String email : userEmails) {
            emailService.sendEmail(email, subject, body);
        }
    }

    private void notifyUser(String email, String subject, String body) {
        emailService.sendEmail(email, subject, body);
    }

}
