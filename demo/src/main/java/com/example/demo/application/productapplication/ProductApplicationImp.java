package com.example.demo.application.productapplication;

import java.util.UUID;

import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.productdomain.Product;
import com.example.demo.domain.productdomain.ProductRepository;
import com.example.demo.dto.productdto.*;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductApplicationImp extends ApplicationBase<Product, UUID> implements ProductApplication {
    
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Logger logger;

    @Autowired
    public ProductApplicationImp(final ProductRepository productRepository, final ModelMapper modelMapper, final Logger logger){
        super((id)-> productRepository.findById(id));
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
    }

    @Override
    public ProductDTO create(CreateUpdateProductDTO dto){
        Product product = this.modelMapper.map(dto, Product.class);
        product.setId(UUID.randomUUID());
        product.validate("name", product.getName(), (name)-> this.productRepository.exists(name));
        this.productRepository.create(product);
        this.logger.info(this.serializeObject(product, "created"));
        return this.modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO update(UUID id, CreateUpdateProductDTO dto){
        Product product = this.findById(id);
        if(product.getName().equals(dto.getName())){
            this.modelMapper.map(dto, product);
            product.validate();
        }else{
            this.modelMapper.map(dto, product);
            product.validate("name", product.getName(), (name)-> this.productRepository.exists(name));
        }
        this.productRepository.update(product);
        logger.info(this.serializeObject(product, "updated"));
        return this.modelMapper.map(product, ProductDTO.class);
    }
}
