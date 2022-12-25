package filesprocessing.Section;
import filesprocessing.Filters.Filter;
import filesprocessing.Filters.FiltersFactory;
import filesprocessing.Order.Order;
import filesprocessing.Order.OrderFactory;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing single section in the command file.
 */
public class SectionModule {
    private static final String NOT = "NOT";
    private static final String REVERSED = "REVERSE";

    /**
     * A method that generates new filter objects using FilterFactory.
     * @param filterValuesArray string array containing all the filter values.
     * @param filterLine line number
     * @return a filter object.
     */
    public static Filter generateFilter(String[] filterValuesArray,int filterLine){
        boolean isNot = false;
        String filterName;
        String val1;
        String val2;
        Filter newFilter;
        ArrayList<String> filterValues = new ArrayList<String>(Arrays.asList(filterValuesArray));
        if(filterValues.contains(NOT)){
            filterValues.remove(NOT);
            isNot = true;
        }
        if (filterValues.size() == 1){
            filterName = filterValues.get(0);
             newFilter = FiltersFactory.createFilter(filterName,null,null,filterLine,isNot);
        }
        else if(filterValues.size() ==2){
            filterName = filterValues.get(0);
            val1 = filterValues.get(1);
             newFilter = FiltersFactory.createFilter(filterName,val1,null,filterLine,isNot);
        }
        else {
            filterName = filterValues.get(0);
            val1 = filterValues.get(1);
            val2 = filterValues.get(2);
             newFilter = FiltersFactory.createFilter(filterName,val1,val2,filterLine,isNot);
        }
        return newFilter;
    }

    /**
     * A method thar generates new order objects using OrderFactory.
     * @param orderValuesArray string array containing order values.
     * @param orderLine line number
     * @return order objects.
     */
    public  static Order generateOrder(String[] orderValuesArray, int orderLine ){
        boolean isReversed = false;
        String orderName;
        ArrayList<String> orderValues = new ArrayList<String>(Arrays.asList(orderValuesArray));

        if(orderValues.contains(REVERSED)){
            orderValues.remove(REVERSED);
            isReversed = true;
        }
        orderName = orderValues.get(0);
        Order newOrder = OrderFactory.createOrder(orderName,orderLine,isReversed);
        return newOrder;
    }

}
