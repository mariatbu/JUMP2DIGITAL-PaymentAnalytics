package com.example.demo.dto.ticketdto;

import java.util.UUID;

import com.example.demo.domain.ticketdomain.PaymentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @NoArgsConstructor @Getter @Setter class TicketDTO {
    
    private UUID id;
    private UUID productId;
    private int amount;
    private PaymentType paymentType;
}
