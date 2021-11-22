package com.example.demo.domain.productdomain;

import java.util.UUID;
import java.math.BigDecimal;

public interface ProductProjection {

    UUID getId();

    String getName();

    BigDecimal getPrice();

    ProductType getDescription(); //TODO: See if this is a String
}
