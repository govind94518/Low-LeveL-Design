package com.shivaya.strategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivaya.dtos.BooleanFilterTypeData;
import com.shivaya.dtos.Product;
import java.util.*;

public class ORFilteringCriteria implements IFilteringCriteria{
    ObjectMapper objectMapper = new ObjectMapper();
    List<IFilteringCriteria> iFilteringCriterias = new ArrayList<>();
    IFilteringCriteria nameFilter;
    IFilteringCriteria priceFilter;
    IFilteringCriteria categoryFilter;
    IFilteringCriteria ORFilteringCriteria;
    IFilteringCriteria ANDFilteringCriteria;

    public ORFilteringCriteria() {
        nameFilter = new NameFilteringCriteria();
        priceFilter = new PriceFilteringCriteria();
        categoryFilter = new CategoryFilter();
        ORFilteringCriteria = new ORFilteringCriteria();
        iFilteringCriterias = Arrays.asList(nameFilter, priceFilter, categoryFilter, ORFilteringCriteria, ANDFilteringCriteria);

    }

    @Override
    public boolean doesSupport(String filterName) {
          return filterName.equals("OR");
    }

    /**
     *
     *     OR: {
         *          left: { PRICE: { min: 1, max: 100 } }
         *          right: {CATEGORY: phone}
     *      }

     OR: {
             left: {
                 AND:  {
                         left: { PRICE: { min: 1, max: 100 } }
                         right: { CATEGORY: phone }
                 }
            }
             right: {
                     NAME: {"value": "iPhone", "matchWay": "EQUALS"}
                }
     }
     */

    @Override
    public boolean doesProductMatch(Product product, Object filterData) {
        Map<String,Object>filteringCriteria = (Map<String,Object>)filterData;
        final BooleanFilterTypeData booleanFilterTypeData ;
        try {
            booleanFilterTypeData = objectMapper.readValue( (String) filteringCriteria.get("OR"), BooleanFilterTypeData.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
       String leftKey = String.valueOf(booleanFilterTypeData.getLeft().keySet().stream().findFirst());

        IFilteringCriteria leftFilteringCriteria = iFilteringCriterias
                .stream()
                .filter(iFilteringCriteria -> iFilteringCriteria.doesSupport(leftKey))
                .findFirst()
                .orElse(null);
        if(Objects.isNull(leftFilteringCriteria)){
            throw new RuntimeException("does not support Filtering Criteria");
        }

        boolean leftMatch = leftFilteringCriteria.doesProductMatch(product, booleanFilterTypeData.getLeft().get(leftKey));
        /*
                String key = left.get("key")
                if(key.equals("OR"))
                if(key.equals("AND"))
                if(key.equals("price"))
                if(key.equals("NOT"))
                if(key.equals("category"))
                if(key.equals("phone"))
          */

        String rightKey = String.valueOf(booleanFilterTypeData.getLeft().keySet().stream().findFirst());

        IFilteringCriteria rightFilteringCriteria = iFilteringCriterias
                .stream()
                .filter(iFilteringCriteria -> iFilteringCriteria.doesSupport(rightKey))
                .findFirst()
                .orElse(null);
        if(Objects.isNull(rightFilteringCriteria)){
            throw new RuntimeException("does not support Filtering Criteria");
        }

        boolean rightMatch = leftFilteringCriteria.doesProductMatch(product, booleanFilterTypeData.getRight().get(rightKey));
        return leftMatch || rightMatch;
    }
}
