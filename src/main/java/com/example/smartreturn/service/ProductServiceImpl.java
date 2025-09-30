package com.example.smartreturn.service;

import com.example.smartreturn.dto.ProductDto;
import com.example.smartreturn.exception.ProductNotFoundException;
import com.example.smartreturn.model.Product;
import com.example.smartreturn.repository.ProductRepo;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl  implements ProductService{
    private final ProductRepo productRepo;
   private final CacheManager cacheManager;
    public ProductServiceImpl(ProductRepo productRepo, CacheManager cacheManager) {
        this.productRepo = productRepo;
        this.cacheManager = cacheManager;
    }

    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#result.id")
    public Product addProduct(ProductDto productDto) {
       Optional<Product> product = productRepo.findById(productDto.getProductId());

        Product product1 = new Product();
        if (product.isPresent()) {
         return null;
        }else{
            product1.setProductName(productDto.getProductName());
            product1.setPrice(productDto.getPrice());
            product1 = productRepo.save(product1);
//          Cache cache= cacheManager.getCache("PRODUCT_CACHE");
//          cache.put(product1.getId(),product1);
        }
        return product1;
    }

    @Override
    @Cacheable(value = "PRODUCT_CACHE",key = "#result.id")
    public ProductDto getProduct(Long id){
        Product product=productRepo.findById(id).orElseThrow(()
                -> new ProductNotFoundException("product not found.."+id));

        ProductDto productDto =new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setPrice(product.getPrice());
      return productDto;
    }
    @Override
    public List<Product> getAllProducts(){
        List<Product>products=productRepo.findAll();
        return products.stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "PRODUCT_CACHE",key = "#id")
    public String deleteProduct(Long id){
        Optional<Product> product=productRepo.findById(id);
        if(product.isPresent()) {
            productRepo.deleteById(product.get().getId());
        }else {
            return "product not found with Id"+id;
        }
        return "product deleted"+id;
    }

    @Override
    @CachePut(value = "PRODUCT_CACHE",key = "#result.id")
    public Product update(ProductDto productDto) {
        Product product = productRepo.findById(productDto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("product not found with this Id" + productDto.getProductId()));
        Product product1 = null;
        if (product != null) {
            product.setProductName(productDto.getProductName());
            product.setPrice(productDto.getPrice());
            product1 = productRepo.save(product);
        }
        return product1;
    }
}
