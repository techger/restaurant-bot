package com.menu.Model;

/**
 * Created by Tortuvshin on 5/14/2016.
 */
public class Product {
    long id;
    String title;
    String description;
    double cost;
    String image;
    double totalCost;
    int totalOrder;
    double ratinng;

    public Product(String title, String description, double cost, String image, double totalCost, int totalOrder) {
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.image = image;
        this.totalCost = totalCost;
        this.totalOrder = totalOrder;
    }

    public Product(String title, String description, double cost, String image) {
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.image = image;
        this.totalCost = cost;
        this.totalOrder = 1;
    }

}
