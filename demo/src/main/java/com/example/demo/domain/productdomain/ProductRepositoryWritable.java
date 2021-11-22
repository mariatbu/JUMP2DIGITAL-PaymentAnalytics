package com.example.demo.domain.productdomain;

import com.example.demo.core.functionalinterfaces.*;

import java.util.UUID;

public interface ProductRepositoryWritable extends FindById<Product, UUID>, ExistsByField{
    public void create(Product product);
}
