package com.portfolio.frogs_product_manager.service;

import com.portfolio.frogs_product_manager.dto.ProductDto;
import com.portfolio.frogs_product_manager.model.Product;
import com.portfolio.frogs_product_manager.repository.ProductRepository;
import com.portfolio.frogs_product_manager.utils.Exceptions;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product getByUuid(UUID id) {
        return (Product) productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be a valid positive number");
        }
        Product product = new Product();
        UUID id = UUID.randomUUID();
        product.setId(id);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        String color = productDto.getColor() != null && !productDto.getColor().isEmpty()
                ? productDto.getColor()
                : "default_color";
        product.setColor(color);

        Optional<String> description = productDto.getDescription();
        product.setDescription(description.isPresent() && !description.get().isEmpty() ? description : Optional.empty());

        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, ProductDto productDto) {
        Optional<Object> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = (Product) optionalProduct.get();
            if (productDto.getName() == null || productDto.getName().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (productDto.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be a valid positive number");
            }
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            String color = (productDto.getColor() != null && !productDto.getColor().isEmpty()) ? productDto.getColor() : "default_color";
            product.setColor(color);
            String description = (productDto.getDescription() != null && productDto.getDescription().isPresent() && !productDto.getDescription().get().isEmpty()) ? productDto.getDescription().get() : null;
            product.setDescription(Optional.ofNullable(description));
            return productRepository.save(product);
        } else {
            throw new Exceptions.ResourceNotFoundException("Product not found with id " + id);
        }
    }

    public Product partiallyUpdateProduct(UUID id, Map<String, Object> updates) {
        Optional<Object> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = (Product) optionalProduct.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        if (value instanceof String && !((String) value).isEmpty()) {
                            product.setName((String) value);
                        } else {
                            throw new IllegalArgumentException("Name cannot be empty");
                        }
                        break;
                    case "price":
                        if (value instanceof Double && (Double) value > 0) {
                            product.setPrice((Double) value);
                        } else {
                            throw new IllegalArgumentException("Price must be a valid positive number");
                        }
                        break;
                    case "color":
                        String color = (value instanceof String && !((String) value).isEmpty()) ? (String) value : "default_color";
                        product.setColor(color);
                        break;
                    case "description":
                        String description = (value instanceof String && !((String) value).isEmpty()) ? (String) value : null;
                        product.setDescription(Optional.ofNullable(description));
                        break;
                }
            });
            return productRepository.save(product);
        } else {
            throw new Exceptions.ResourceNotFoundException("Product not found with id " + id);
        }
    }

    public void deleteProduct(UUID id) {
        Product product = getByUuid(id);
        productRepository.delete(product);
    }
}
