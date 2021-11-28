package com.example.demo.domain.ticketdomain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.core.EntityBase;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Table(name = "tickets")
@Entity
public @NoArgsConstructor @Setter @Getter class Ticket extends EntityBase {
    
    @NotBlank
    @Column(name = "productId")
    private UUID productId;

    @DecimalMin(value = "0.0", inclusive = false)
    private int amount;

    @NotNull
    @Column(name = "paymentType")
    private PaymentType paymentType;

    @Override
    public String toString() {
        return String.format("Ticket {id: %s, productId: %s, amount: %s, paymentType: %s}", this.getId(), this.getProductId(),
                this.getAmount(), this.getPaymentType());
    }

}
