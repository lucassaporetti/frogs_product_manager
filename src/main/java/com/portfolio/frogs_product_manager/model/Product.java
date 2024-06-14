package com.portfolio.frogs_product_manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;
import java.util.Optional;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private double price;

    @Column(nullable = true, columnDefinition = "varchar(255) default 'default_color'")
    private String color = "default_color";

    @Column(nullable = true)
    private String description;


    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = (color != null) ? color : "default_color";
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Optional<String> description) {
        this.description = description.orElse(null); // Define null se Optional vazio
    }
}
