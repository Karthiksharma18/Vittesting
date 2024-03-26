package com.example.mongo.controller;

import com.example.mongo.model.Product;
import com.example.mongo.repository.ProductRepository;
import com.example.mongo.resource.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getALlProducts() {
        return ResponseEntity.ok(this.productRepository.findAll());
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest){
       Product product = new Product();
       product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());


        return ResponseEntity.status(201).body(this.productRepository.save(product));
    }
    @PostMapping("/submit-blood-group")
    public ResponseEntity<String> submitBloodGroup(@RequestBody String bloodGroup) {
        // Save the blood group to the database
        Product existingProduct = productRepository.findByDescription(bloodGroup);
        if(existingProduct != null) {
            // If the blood group already exists, increment the count
            existingProduct.setCount(existingProduct.getCount() + 1);
            productRepository.save(existingProduct);
        }else {
            // If the blood group does not exist, create a new product
            Product product = new Product();
            product.setName("Blood Group");
            product.setDescription(bloodGroup);
            product.setCount(1);
            productRepository.save(product);
        }
        return ResponseEntity.ok("Blood group submitted successfully!");

    }

}
