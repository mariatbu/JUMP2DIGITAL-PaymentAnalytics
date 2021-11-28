package com.example.demo.dto.ticketdto;

import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.example.demo.core.validators.EnumNamePattern;
import com.example.demo.core.validators.ValueOfEnum;
import com.example.demo.domain.ticketdomain.PaymentType;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
public @NoArgsConstructor @Getter @Setter class CreateUpdateTicketDTO {
    
    @NotNull
    private UUID productId;

    @DecimalMin(value = "0", inclusive = false)
    private int amount;

    @NotNull
    @EnumNamePattern(regexp = "VISA|MASTERCARD")
    private PaymentType paymentType;
}
