package com.shivaya.strategy;


public class EqualsMatchingStrategy implements IStringMatchingFilteringCriteria {
    @Override
    public boolean doesSupportStringMatching(String filterName) {
        return filterName.equals("equals");
    }

    @Override
    public boolean applyStringMatching(String productName, String productFilterName) {
        return productName.equals(productFilterName);
    }
}
