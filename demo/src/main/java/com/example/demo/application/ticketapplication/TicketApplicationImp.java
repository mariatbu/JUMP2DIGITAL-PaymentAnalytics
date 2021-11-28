package com.example.demo.application.ticketapplication;

import java.util.UUID;

import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.ticketdomain.Ticket;
import com.example.demo.domain.ticketdomain.TicketRepository;
import com.example.demo.dto.ticketdto.CreateUpdateTicketDTO;
import com.example.demo.dto.ticketdto.TicketDTO;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketApplicationImp extends ApplicationBase<Ticket, UUID> implements TicketApplication{

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final Logger logger;

    @Autowired
    public TicketApplicationImp(final TicketRepository ticketRepository, final ModelMapper modelMapper, final Logger logger){
        super((id)-> ticketRepository.findById(id));
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
    }

    @Override
    public TicketDTO create(CreateUpdateTicketDTO dto) {
        Ticket ticket = this.modelMapper.map(dto, Ticket.class);
        ticket.setId(UUID.randomUUID());
        this.ticketRepository.create(ticket);
        this.logger.info(this.serializeObject(ticket, "created"));
        return this.modelMapper.map(ticket, TicketDTO.class);
    }
    
}
