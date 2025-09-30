package com.example.smartreturn.service;

import com.example.smartreturn.dto.ProductDto;
import com.example.smartreturn.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);

    ProductDto getProduct(Long id);

    List<Product> getAllProducts();

    String deleteProduct(Long id);

    Product update(ProductDto productDto);
}
