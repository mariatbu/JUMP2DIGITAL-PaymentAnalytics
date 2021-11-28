package com.example.demo.infraestructure.ticketinfraestructure;

import java.util.UUID;

import com.example.demo.domain.ticketdomain.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepositoryJPA extends JpaRepository<Ticket, UUID>{
    
}
