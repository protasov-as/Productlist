package com.example.productlist.models;

import org.springframework.stereotype.Component;

@Component
public class ListResponse {
    int totalKcal;
    List productList;

    public ListResponse(List productList, int totalKcal) {
        this.totalKcal = totalKcal;
        this.productList = productList;
    }

    public ListResponse() {
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public List getProductList() {
        return productList;
    }

    public void setTotalKcal(int totalKcal) {
        this.totalKcal = totalKcal;
    }

    public void setProductList(List productList) {
        this.productList = productList;
    }
}
