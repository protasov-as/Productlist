package com.example.productlist.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "lists")
public class List {

    @Id
    private long id;
    private String name;
    private java.util.List<Product> productList;

    public List(long id, String name) {
        this.id = id;
        this.name = name;
        this.productList = new ArrayList<>();
    }

    public List() {
    }

    public List(String name) {
        this.id = this.hashCode();
        this.name = name;
        this.productList = new ArrayList<>();
//        this.productList.add(new Product("nn", "dd", 777));
//        this.productList.add(new Product("nnNN", "ddDD", 77799));
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

    public java.util.List<Product> getProductList() {
        return productList;
    }

    public void setProductList(java.util.List<Product> productList) {
        this.productList = productList;
    }
}
