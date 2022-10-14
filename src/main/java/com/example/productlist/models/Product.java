package com.example.productlist.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private long id;
    private String name;
    private String description;
    private int kcal;

    public Product(String name, String description, int kcal) {
        this.id = this.hashCode();
        this.name = name;
        this.description = description;
        this.kcal = kcal;
    }

    public Product(long id, String name, String description, int kcal) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kcal = kcal;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }
}
