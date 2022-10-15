package com.example.productlist.models;

public class ListResponse {
    private int totalKcal;
    private List productList;

    public ListResponse(List productList, int totalKcal) {
        this.totalKcal = totalKcal;
        this.productList = productList;
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public List getProductList() {
        return productList;
    }
}
