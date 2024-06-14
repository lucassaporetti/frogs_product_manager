package com.portfolio.frogs_product_manager.controller;

import com.portfolio.frogs_product_manager.dto.ProductDto;
import com.portfolio.frogs_product_manager.model.Product;
import com.portfolio.frogs_product_manager.service.ProductService;

import com.portfolio.frogs_product_manager.utils.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Wait...");
        return "Hello world";
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public List<Product> listAll() {
        return productService.listAll();
    }

    @GetMapping("get_one/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        Product product = productService.getByUuid(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/general_update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/partial_update/{id}")
    public ResponseEntity<Product> partiallyUpdateProduct(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Product updatedProduct = productService.partiallyUpdateProduct(id, updates);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("delete_one/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exceptions.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
