package com.example.demo.infraestructure.productinfraestructure;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.productdomain.Product;
import com.example.demo.domain.productdomain.ProductProjection;

public interface ProductRepositoryJPA extends JpaRepository<Product, UUID> {

    Product findByName(@Param("name") String name);
    String sqlSelect = "SELECT p.id as id, p.name as name, p.price as price, p.description as description FROM Product p WHERE (:name is NULL OR name LIKE %:name%)";
    @Query(sqlSelect)

    List<ProductProjection> findByCriteria(@Param("name") String name, Pageable pageable);
    final String sqlExists = "SELECT case WHEN count(p)>0 then true else false end FROM Product p where p.name=:name";
    @Query(sqlExists)
    boolean exists(@Param("name") String name);
}
