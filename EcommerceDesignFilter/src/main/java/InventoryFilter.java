import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import com.shivaya.strategy.CategoryFilter;
import com.shivaya.strategy.IFilteringCriteria;
import com.shivaya.strategy.NameFilteringCriteria;
import com.shivaya.strategy.PriceFilteringCriteria;
import com.shivaya.dtos.Product;


import java.util.*;

public class InventoryFilter {

    List<IFilteringCriteria> iFilteringCriterias = new ArrayList<>();

    IFilteringCriteria nameFilter;
    IFilteringCriteria priceFilter;
    IFilteringCriteria categoryFilter;


    public InventoryFilter() {
        nameFilter = new NameFilteringCriteria();
        priceFilter = new PriceFilteringCriteria();
        categoryFilter = new CategoryFilter();
        iFilteringCriterias = Arrays.asList(nameFilter, priceFilter, categoryFilter);
    }
//
//    public List<Product> filterProducts(List<Product>products, Map<String,Object> filterProps){
//        for(Product product : products){
//
//        for(String key : filterProps.keySet()){
//            if(key.equals("name")){
//                if (filterProps.get(key).equals(product.getName())) {
//                    filteredProduct.add(product);
//                    break;
//                }
//
//    }
//
//            if(key.equals("price")){
//                    Map<String, Object> productPrice = filterProps.get(key);
//                    long mn = productPrice.get("mn");
//                    long mx = productPrice.get("mx");
//                    if (mn <= product.getPrice() and mx>=product.getPrice()){
//                    filteredProduct.add(product);
//                    break;
//                }
//    }
//
//            if(key.equals("category"){
//                    if (product.getCategory().contains(filterProps.get(key))) {
//                        filteredProduct.add(product);
//                        break;
//                    }
//            }
//
//        }
//        return filteredProduct;
//        /*
//             Problems in above code:-> OCP compliant
//             in this either we can implement whole OR or AND Condition , both OR & AND both Condition are not applied

//         ```
//    Limitations:
//            * OCP breaks for new type of filter property. - solved - from 1 class change to 0 class change now.
//            * Multiple categories instead of just one.
//            * contains/prefix/suffix check instead of equals
//* System is fragile. Specially in terms of type safety for fitlerProp values
//* Either only "OR" is implemented or only "AND" is implemented. Change to another needs code change.
//            * Combination is not possible.
//            * No min in price or no max in price.
//
//* Price having currency filter
//	* Looking like new type of filter.
//         */

    /**

     CATEGORY: phone,
     PRICE: { min: 1, max: 100 }

     OR: {
         left: { PRICE: { min: 1, max: 100 } }
         right: {CATEGORY: phone}
     }

     AND: {
     left: {PRICE: {min: 1, max: 100}}
     right: {CATEGORY: phone}
     }

     NOT: {
     operand: {PRICE: {min: 1, max: 100}}
     }

     OR: {
         left: {
                 AND:  {
                             left: { PRICE: {min: 1, max: 100 } }
                             right: { CATEGORY: phone }
                    }
            }
        right: {
                    NAME: {"value": "iPhone", "matchWay": "EQUALS"}
            }

     }
     */


//    }


    @SneakyThrows
    public List<Product> filterProducts(List<Product> products, Map<String, Object> filterProps) throws JsonProcessingException {
        List<Product> filteredProduct = new ArrayList<>();
        for (Product product : products) {

            for (String key : filterProps.keySet()) {

                if(key.equals("OR")){
                        IFilteringCriteria filteringCriteria = iFilteringCriterias
                                .stream()
                                .filter(iFilteringCriteria -> iFilteringCriteria.doesSupport(key))
                                .findFirst()
                                .orElse(null);
                        if(Objects.isNull(filteringCriteria)){
                            throw new RuntimeException("does not support Filtering Criteria");
                        }

                        if(filteringCriteria.doesProductMatch(product, filterProps.get(key))){
                            filteredProduct.add(product);
                        }

                }
                if(key.equals("AND")){
                    boolean filteringCriteriaSatisfied = iFilteringCriterias
                            .stream()
                            .allMatch(iFilteringCriteria -> iFilteringCriteria.doesSupport(key) && iFilteringCriteria.doesProductMatch(product, filterProps.get(key)));
                    if(filteringCriteriaSatisfied){
                        filteredProduct.add(product);
                    }

                }

            }

        }
        return filteredProduct;
    }





}
