package com.example.demo.infraestructure.productinfraestructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.domain.productdomain.Product;
import com.example.demo.domain.productdomain.ProductProjection;
import com.example.demo.domain.productdomain.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public List<ProductProjection> getAll(String name, int page, int size){
        return this.productRepositoryJPA.findByCriteria(name, PageRequest.of(page, size));
    }
}
