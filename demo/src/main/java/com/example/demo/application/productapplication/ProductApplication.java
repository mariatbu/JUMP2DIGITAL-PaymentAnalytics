package com.example.demo.application.productapplication;

import java.util.List;
import java.util.UUID;

import com.example.demo.domain.productdomain.ProductProjection;
import com.example.demo.dto.productdto.CreateUpdateProductDTO;
import com.example.demo.dto.productdto.ProductDTO;

public interface ProductApplication {
    
    public ProductDTO create(CreateUpdateProductDTO dto);
    public ProductDTO update(UUID id, CreateUpdateProductDTO dto);
    public void delete(UUID id);
    public ProductDTO get(UUID id);
    public List<ProductProjection> getAll(String name, int page, int size);
}
