package com.example.demo.infraestructure.ticketinfraestructure;

import java.util.Optional;
import java.util.UUID;

import com.example.demo.domain.ticketdomain.Ticket;
import com.example.demo.domain.ticketdomain.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryJPAImp implements TicketRepository{
    
    private final TicketRepositoryJPA ticketRepositoryJPA;

    @Autowired
    public TicketRepositoryJPAImp (final TicketRepositoryJPA ticketRepositoryJPA){
        this.ticketRepositoryJPA = ticketRepositoryJPA;
    }

    @Override
    public void create(Ticket ticket) {
        this.ticketRepositoryJPA.save(ticket);        
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return this.ticketRepositoryJPA.findById(id);
    }

    @Override
    public boolean exists(String productId) {
        UUID productUuidId = UUID.fromString(productId);
        return this.ticketRepositoryJPA.existsById(productUuidId);
    }
}
