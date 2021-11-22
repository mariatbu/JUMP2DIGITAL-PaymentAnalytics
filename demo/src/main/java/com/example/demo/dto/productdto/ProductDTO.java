package com.example.demo.dto.productdto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @Getter @Setter @NoArgsConstructor class ProductDTO {
    
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
}
