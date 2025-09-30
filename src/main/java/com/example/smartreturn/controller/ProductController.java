package com.example.smartreturn.controller;

import com.example.smartreturn.dto.ProductDto;
import com.example.smartreturn.model.Product;
import com.example.smartreturn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.RequestEntity.*;

@RestController
@RequestMapping("/product/apis")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto){
        Product saveProd =productService.addProduct(productDto);
        return ResponseEntity.ok(saveProd);
    }
    @GetMapping
    public ResponseEntity<ProductDto>product(@RequestParam  Long id){
        ProductDto productDto= productService.getProduct(id);
        return  ResponseEntity.ok(productDto);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>>getAllProduct(){
        List<Product> productDto= productService.getAllProducts();
        return  ResponseEntity.ok(productDto);
    }
    @PutMapping
    public ResponseEntity<Product> update(@RequestBody ProductDto productDto){
        Product saveProd =productService.update(productDto);
        return ResponseEntity.ok(saveProd);
    }

    @DeleteMapping
    public ResponseEntity<String>delete(@RequestParam Long id){
      String msg=  productService.deleteProduct(id);
      return ResponseEntity.ok(msg);
    }


}
