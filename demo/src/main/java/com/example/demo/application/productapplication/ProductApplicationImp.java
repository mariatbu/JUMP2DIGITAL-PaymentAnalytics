package com.example.demo.application.productapplication;

import java.util.UUID;

import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.productdomain.Product;
import com.example.demo.domain.productdomain.ProductRepository;
import com.example.demo.dto.productdto.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductApplicationImp extends ApplicationBase<Product, UUID> implements ProductApplication {
    
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductApplicationImp(final ProductRepository productRepository, final ModelMapper modelMapper){
        super((id)-> productRepository.findById(id));
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO create(CreateUpdateProductDTO dto){
        Product product = this.modelMapper.map(dto, Product.class);
        product.setId(UUID.randomUUID());
        product.validate("name", product.getName(), (name)-> this.productRepository.exists(name));
        this.productRepository.create(product);
        return this.modelMapper.map(product, ProductDTO.class);
    }
}
