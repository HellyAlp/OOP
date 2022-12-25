package filesprocessing.Filters;

/**
 * A class that represents a factory class for creating Order objects.
 */
public class FiltersFactory {
    private static   final String GREATER_THAN_FILTER="greater_than";
    private static   final String BETWEEN_FILTER="between";
    private static   final String SMALLER_THAN_FILTER="smaller_than";
    private static   final String FILE_FILTER="file";
    private static   final String CONTAINS_FILTER="contains";
    private static   final String PREFIX_FILTER ="prefix";
    private static   final String SUFFIX_FILTER ="suffix";
    private static   final String WRITABLE_FILTER="writable";
    private static   final String EXECUTABLE_FILTER="executable";
    private static   final String HIDDEN_FILTER="hidden";
    private static   final String ALL_FILTER="all";
    private static   final String WARNING= "Warning in line ";
    private static   final String YES= "YES";
    private static   final String NO= "NO";
    private static   Filter newFilter;


    /**
     * a method that gets the filter name,all filter values and line and creates new filter object according to
     * the filter given.
     * @return filter object
     */
    public static Filter createFilter(String filterName, String val1, String val2, int line, boolean isNot){
        switch (filterName) {
            case GREATER_THAN_FILTER:
                if(val1!=null&&Double.parseDouble(val1) >= 0 && val2==null){
                    newFilter = new GreaterThanFilter(isNot, val1);
                    return newFilter;
                }
                else {
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case BETWEEN_FILTER:
                if(Double.parseDouble(val1) >= 0 && Double.parseDouble(val2) >= 0 && Double.parseDouble(val2) >=
                        Double.parseDouble(val1))
                {
                    newFilter = new BetweenFilter(isNot, val1, val2);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case SMALLER_THAN_FILTER:
                if(val1!=null&&Double.parseDouble(val1) >= 0 && val2==null){
                    newFilter = new SmallerThanFilter(isNot, val1);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case FILE_FILTER:
                if (val2==null){
                    newFilter = new FileFilter(val1, isNot);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }

            case CONTAINS_FILTER:
                if (val2 ==null){
                    newFilter = new ContainsFilter(val1, isNot);
                    return newFilter;
                }
                else{
                System.err.println(WARNING+line);
                newFilter = new AllFilter(false);
                return newFilter;
            }
            case PREFIX_FILTER:
                if (val2 ==null){
                    newFilter = new PrefixFilter(val1, isNot);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }

            case SUFFIX_FILTER:
                if (val2 == null){
                    newFilter = new SuffixFilter(val1, isNot);
                    return newFilter;
                }
                else{
                System.err.println(WARNING+line);
                newFilter = new AllFilter(false);
                return newFilter;
            }
            case WRITABLE_FILTER:
                if (val1!=null&&(val1.equals(YES) || val1.equals(NO)) && (val2==null) ){
                    newFilter = new WriteableFilter(val1, isNot);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case EXECUTABLE_FILTER:
                if (val1!=null&&(val1.equals(YES) || val1.equals(NO))){
                    newFilter = new ExecutableFilter(val1, isNot);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case HIDDEN_FILTER:
                if (val1!=null&&(val1.equals(YES) || val1.equals(NO)) && (val2==null) ){
                    newFilter = new HiddenFilter(val1, isNot);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
            case ALL_FILTER:
                if (val1==null && val2 == null){
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
                else{
                    System.err.println(WARNING+line);
                    newFilter = new AllFilter(false);
                    return newFilter;
                }
        }
        System.err.println(WARNING+line);
        newFilter = new AllFilter(false);
        return newFilter;
    }
}
