package com.example.demo.domain.productdomain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;

import org.springframework.validation.annotation.Validated;

import com.example.demo.core.EntityBase;
import com.example.demo.core.validators.ValueOfEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Table(name = "products")
@Entity
public @NoArgsConstructor @Setter @Getter class Product extends EntityBase {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public String toString() {
        return String.format("Product {id: %s, name: %s, price: %s, description: %s}", this.getId(), this.getName(),
                this.getPrice(), this.getDescription());
    }

}
