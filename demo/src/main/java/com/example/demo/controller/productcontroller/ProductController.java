package com.example.demo.controller.productcontroller;

import javax.validation.Valid;

import com.example.demo.dto.productdto.ProductDTO;
import com.example.demo.dto.productdto.CreateUpdateProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.application.productapplication.ProductApplication;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {
    
    private final ProductApplication productApplication;

    @Autowired
    public ProductController(ProductApplication productApplication){
        this.productApplication = productApplication;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid final CreateUpdateProductDTO dto){
        ProductDTO productDTO = this.productApplication.create(dto);
        return ResponseEntity.status(201).body(productDTO);
    }
}
