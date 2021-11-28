package com.example.demo.domain.ticketdomain;

import java.util.UUID;

import com.example.demo.core.functionalinterfaces.ExistsByField;
import com.example.demo.core.functionalinterfaces.FindById;

public interface TicketRepositoryWritable extends FindById<Ticket, UUID>, ExistsByField{
    public void create(Ticket ticket);
}
