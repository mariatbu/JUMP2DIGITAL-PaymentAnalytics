package com.example.demo.controller.productcontroller;

import java.util.UUID;

import javax.validation.Valid;

import com.example.demo.application.productapplication.ProductApplication;
import com.example.demo.dto.productdto.CreateUpdateProductDTO;
import com.example.demo.dto.productdto.ProductDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody CreateUpdateProductDTO dto){
        ProductDTO productDTO = this.productApplication.update(id, dto);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping(path="/{id}")
    public void delete(@PathVariable UUID id){
        this.productApplication.delete(id);
        ResponseEntity.ok("deleted");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path="/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id){
        ProductDTO productDTO = this.productApplication.get(id);
        return ResponseEntity.ok(productDTO);
    }

    //TODO: fix
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(
        @RequestParam(required = false) String name,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.status(200).body(this.productApplication.getAll(name, page, size));
    }
}
