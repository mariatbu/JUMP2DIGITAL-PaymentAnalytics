package com.example.demo.application.productapplication;

import com.example.demo.dto.productdto.CreateUpdateProductDTO;
import com.example.demo.dto.productdto.ProductDTO;

public interface ProductApplication {
    
    public ProductDTO create(CreateUpdateProductDTO dto);
}