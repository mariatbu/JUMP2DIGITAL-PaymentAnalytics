package com.example.demo.infraestructure.productinfraestructure;

import com.example.demo.domain.productdomain.ProductRepository;
import com.example.demo.domain.productdomain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public class ProductRepositoryJPAImp implements ProductRepository{
    
    private final ProductRepositoryJPA productRepositoryJPA;

    @Autowired
    public ProductRepositoryJPAImp(final ProductRepositoryJPA productRepositoryJPA){
        this.productRepositoryJPA = productRepositoryJPA;
    }

    public void create(Product product){
        this.productRepositoryJPA.save(product);
    }

    public Optional<Product> findById(UUID id){
        return this.productRepositoryJPA.findById(id);
    }

    @Override
    public boolean exists(String name){
        return this.productRepositoryJPA.exists(name);
    }

    @Override
    public void update(Product product){
        this.productRepositoryJPA.save(product);
    }

    @Override
    public void delete(Product product){
        this.productRepositoryJPA.delete(product);
    }
}
