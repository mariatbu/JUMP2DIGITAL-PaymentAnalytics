package com.example.demo.application.ticketapplication;

import com.example.demo.dto.ticketdto.CreateUpdateTicketDTO;
import com.example.demo.dto.ticketdto.TicketDTO;

public interface TicketApplication {
    
    public TicketDTO create(CreateUpdateTicketDTO dto);
}
