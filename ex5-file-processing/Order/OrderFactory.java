package filesprocessing.Order;

/**
 * A class that represents a factory class for creating Order objects.
 */
public class OrderFactory {
    private static final String ABS_ORDER = "abs";
    private static final String TYPE_ORDER = "type";
    private static final String SIZE_ORDER = "size";
    private static   final String WARNING= "Warning in line ";
    private static Order newOrder;

    /**
     * a method that gets the order name and line and creates new order object according to the order given.
     * @return order object
     */
    public static Order createOrder(String orderName, int line,boolean isReversed) {
        switch (orderName) {
            case ABS_ORDER:
                newOrder = new AbsOrder(isReversed);
                return newOrder;
            case TYPE_ORDER:
                newOrder = new TypeOrder(isReversed);
                return newOrder;
            case SIZE_ORDER:
                newOrder = new SizeOrder(isReversed);
                return newOrder;
        }
        System.err.println(WARNING+line);
        newOrder = new AbsOrder(false);
        return newOrder;
    }
}
