package com.example.demo.dto.productdto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
public @Getter @Setter @NoArgsConstructor class CreateUpdateProductDTO {
    
    @NotBlank
    private String name;

    @DecimalMin(value="0.0", inclusive=false)
    private BigDecimal price;

    @NotBlank
    private String description;
}
