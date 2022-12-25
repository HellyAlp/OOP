import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * A class that contains methods that analyze different simple sets performance.
 */
public class SimpleSetPerformanceAnalyzer {
    private  OpenHashSet openHashSetDS = new OpenHashSet();
    private   ClosedHashSet closedHashSetDS = new ClosedHashSet();
    private  TreeSet<String> treeSetDS =  new TreeSet<String>();
    private LinkedList<String> linkedlistDS =  new LinkedList<String>();
    private  HashSet<String> hashSetDS =new HashSet<String>();
    private static SimpleSet[] dataStructuresArray;
    private  static String[] data1 = Ex4Utils.file2array("data1.txt") ;
    private  static String[] data2 = Ex4Utils.file2array("data2.txt") ;
    private final static String  HI = "hi";
    private final static String NUM1 = "-13170890158";
    private final static String NUM2 = "23";
    private final static int ZERO = 0;
    private final static int LINKED_LIST_ITERATION = 7000;
    private final static int ITERATION = 70000;
    private final static int NANO_TO_MILLI = 1000000;
    private final static String SEPARATE_PRINTS = "*******************************************";

    /**
     * constructor the initialize each data structure.
     */
    public SimpleSetPerformanceAnalyzer(){
        dataStructuresArray = new SimpleSet[5];
        dataStructuresArray[0] = openHashSetDS;
        dataStructuresArray[1] = closedHashSetDS;
        dataStructuresArray[2] = new CollectionFacadeSet(treeSetDS);
        dataStructuresArray[3] = new CollectionFacadeSet(linkedlistDS);
        dataStructuresArray[4] =  new CollectionFacadeSet(hashSetDS);

    }

    /**
     * constructor the initialize each data structure with the given data string array.
     */
    public SimpleSetPerformanceAnalyzer(String[] data){
        dataStructuresArray = new SimpleSet[5];
        dataStructuresArray[0] = new OpenHashSet(data);
        dataStructuresArray[1] = new ClosedHashSet(data);
        dataStructuresArray[2] = new CollectionFacadeSet(new TreeSet<String>(Arrays.asList(data)));
        dataStructuresArray[3] = new CollectionFacadeSet(new LinkedList<String>(Arrays.asList(data)));
        dataStructuresArray[4] =  new CollectionFacadeSet(new HashSet<String>(Arrays.asList(data)));
    }

    /**
     * A method that measure the time it takes to a specific data structure to check if it contains a given string.
     * @param value the string we check if the data structure contains.
     * @param dataStructure a given data structure.
     * @param j the index of the data structure in the data structure array.
     */
    public void contains(String value,SimpleSet dataStructure,int j){
        int iter = ITERATION;
        if(dataStructure == dataStructuresArray[3]){
            System.out.println(SEPARATE_PRINTS);
            iter = LINKED_LIST_ITERATION;
            long timeBefore = System.nanoTime();
            for(int i=0;i<iter;i++){
                dataStructure.contains(value);
            }
            long difference = System.nanoTime() - timeBefore;
            System.out.println("time for contains "+value+ " in LinkedList is: "+ difference/iter);
            System.out.println(SEPARATE_PRINTS);
        }
        else {
            System.out.println(SEPARATE_PRINTS);
            for(int i=0;i<iter;i++){
                dataStructure.contains(value);
            }
            long timeBefore = System.nanoTime();
            for(int i=0;i<iter;i++){
                dataStructure.contains(value);
            }
            long difference = System.nanoTime() - timeBefore;
            System.out.println("time for contains " + value+" in dataStructure number "+j+" is:" + difference/iter);
            System.out.println(SEPARATE_PRINTS);
        }
    }

    /**
     * A method that measure the time it takes to a specific data structure to add a given string Array.
     * @param data array of strings to add.
     * @param dataStructure a given data structure.
     * @param j the index of the data structure in the data structure array.
     */
    private void tests1and2(String[] data,SimpleSet dataStructure,int j){
        long timeBefore = System.nanoTime();
        for(String value:data){
            dataStructure.add(value);
        }
        long difference = System.nanoTime() - timeBefore;
        long milli = difference/NANO_TO_MILLI;
        System.out.println(SEPARATE_PRINTS);
        System.out.println("time for adding "+"dataStructure number "+j+"for"+ milli);
        System.out.println(SEPARATE_PRINTS);
    }



    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer analyzier = new SimpleSetPerformanceAnalyzer();
        //First measure: time for adding data1.txt strings to each data structure.
        int j =ZERO;
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.tests1and2(data1,dataStructure,j);
            j++;
        }
       analyzier = new SimpleSetPerformanceAnalyzer();
        j =ZERO;
        //Second measure: time for adding data2.txt strings to each data structure.
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.tests1and2(data2,dataStructure,j);
            j++;
        }
        analyzier = new SimpleSetPerformanceAnalyzer(data1);
        j =ZERO;
        //Third measure: time for check if data structure contains "hi" initialized with data1.txt.
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.contains(HI,dataStructure,j);
            j++;
        }
        //Fourth measure: time for check if data structure contains "hi" initialized with data1.txt.
        //analyzier = new SimpleSetPerformanceAnalyzer(data1);
        j =ZERO;
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.contains(NUM1,dataStructure,j);
            j++;
        }
        //Fifth measure: time for check if data structure contains "hi" initialized with data1.txt.
        analyzier = new SimpleSetPerformanceAnalyzer(data2);
        j =ZERO;
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.contains(NUM2,dataStructure,j);
            j++;
        }
        //Sixth measure: time for check if data structure contains "hi" initialized with data1.txt.
        //analyzier = new SimpleSetPerformanceAnalyzer(data2);
        j =ZERO;
        for (SimpleSet dataStructure: dataStructuresArray){
            analyzier.contains(HI,dataStructure,j);
            j++;
        }

    }

    }
