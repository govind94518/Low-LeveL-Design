package com.shivaya.strategy;
import com.shivaya.dtos.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivaya.dtos.NameFilterTypeData;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class NameFilteringCriteria implements IFilteringCriteria {
    IStringMatchingFilteringCriteria equalsMatchingStrategy;
    IStringMatchingFilteringCriteria prefixMatchingStrategy;
    List<IStringMatchingFilteringCriteria> iStringMatchingFilteringCriteriaList;
    ObjectMapper objectMapper = new ObjectMapper();


    public NameFilteringCriteria() {
        equalsMatchingStrategy = new EqualsMatchingStrategy();
        prefixMatchingStrategy = new PrefixMatchingStrategy();
        iStringMatchingFilteringCriteriaList = Arrays.asList(equalsMatchingStrategy,prefixMatchingStrategy);
    }


    @Override
    public boolean doesSupport(String filterName) {
        return  filterName.equals("name");
    }

    @Override
    public boolean doesProductMatch(Product product, Object filterData)   {
//        Map<String,Object> filteredNames=  (String)filterData;
//        String filterValue = filteredNames.get("value").toString();-->System is fragile it can break the system
//        String matchWay = (String) filteredNames.get("match_way");
       final NameFilterTypeData nameFilterTypeData ;
        try {
           nameFilterTypeData = objectMapper.readValue((String) filterData, NameFilterTypeData.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        // objectMapper.readValue((JsonParser) filteredNames, NameFilterTypeData.class)
        IStringMatchingFilteringCriteria iStringMatchingFilterValue = iStringMatchingFilteringCriteriaList
                .stream()
                .filter(iStringMatchingFilteringCriteria -> iStringMatchingFilteringCriteria.doesSupportStringMatching(nameFilterTypeData.getMatchWay()))
                .findFirst()
                .orElse(null);
        if(Objects.isNull(iStringMatchingFilterValue)){
            throw new RuntimeException("does not support Filtering Criteria");
        }
        return iStringMatchingFilterValue.applyStringMatching(product.getName(), nameFilterTypeData.getValue());
    }


}
