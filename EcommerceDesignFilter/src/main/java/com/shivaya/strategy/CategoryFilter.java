package com.shivaya.strategy;
import com.shivaya.dtos.Product;


import java.util.List;

public class CategoryFilter implements IFilteringCriteria {

    @Override
    public boolean doesSupport(String filterName) {
        return  filterName.equals("category");
    }

    @Override
    public boolean doesProductMatch(Product product, Object filterData) {
        List<String>categories =  (List<String>)filterData;
        return categories
                .stream()
                .anyMatch(category -> product.getCategory().contains(category));
    }

}
