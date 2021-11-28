package com.example.demo.domain.ticketdomain;

import java.util.UUID;

public interface TicketProjection {
    
    UUID getId();

    UUID getProductId();

    int getAmount();

    PaymentType gePaymentType();
}
