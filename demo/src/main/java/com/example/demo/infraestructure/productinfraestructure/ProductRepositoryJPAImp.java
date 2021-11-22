package com.example.demo.infraestructure.productinfraestructure;

import com.example.demo.domain.productdomain.ProductRepository;
import com.example.demo.domain.productdomain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    //@Override
    public boolean exists(String name){
        return this.productRepositoryJPA.exists(name);
    }
}
