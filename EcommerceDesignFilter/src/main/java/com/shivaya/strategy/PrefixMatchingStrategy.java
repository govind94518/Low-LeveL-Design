package com.shivaya.strategy;

public class PrefixMatchingStrategy implements IStringMatchingFilteringCriteria{
    @Override
    public boolean doesSupportStringMatching(String filterName) {
        return filterName.equals("prefix");
    }

    @Override
    public boolean applyStringMatching(String productName, String productFilterName) {
        return productName.contains(productFilterName);
    }
}
