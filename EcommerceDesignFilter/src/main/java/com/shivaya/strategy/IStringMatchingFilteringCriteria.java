package com.shivaya.strategy;

public interface IStringMatchingFilteringCriteria  {
    boolean doesSupportStringMatching(String filterName);
    boolean applyStringMatching(String productName, String productFilterName);
}
