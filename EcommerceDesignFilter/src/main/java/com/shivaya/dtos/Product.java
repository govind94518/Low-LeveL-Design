package com.shivaya.dtos;
import java.util.List;


public class Product {

    String id;

    String name;
    double  price;
    List<String> category;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getCategory() {
        return category;
    }
}
