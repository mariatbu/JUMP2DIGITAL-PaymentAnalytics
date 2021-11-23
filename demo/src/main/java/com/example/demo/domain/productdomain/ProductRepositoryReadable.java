package com.example.demo.domain.productdomain;

import java.util.List;

public interface ProductRepositoryReadable {
    List<ProductProjection> getAll (String name, int page, int size);
}
