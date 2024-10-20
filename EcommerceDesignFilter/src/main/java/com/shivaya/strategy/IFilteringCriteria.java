package com.shivaya.strategy;
import com.shivaya.dtos.Product;


public interface IFilteringCriteria {
    boolean doesSupport(String filterName);
    boolean doesProductMatch(Product product, Object filterData)  ;
}
