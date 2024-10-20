package com.shivaya.strategy;
import com.shivaya.dtos.Product;

import java.util.Map;

public class PriceFilteringCriteria implements IFilteringCriteria {


    @Override
    public boolean doesSupport(String filterName) {
        return filterName.equals("price");
    }

    @Override
    public boolean doesProductMatch(Product product, Object filterData) {
        Map<String,Double>priceRange  = (Map<String, Double>) filterData;
        double mn = priceRange.get("mn");
        double mx = priceRange.get("mx");
        return product.getPrice()>=mn && product.getPrice()<= mx;
    }
}
