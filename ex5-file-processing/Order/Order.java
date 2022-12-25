package filesprocessing.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * An abstract class representing a Order.
 */
public abstract class Order {
    protected Comparator<File> comparator;
    private final double BYTES_TO_KB = 1024;
    private boolean isReversed;

    public Order(boolean reversed){
       isReversed = reversed;
    }


    /**
     * A method implementing mergeSort. a function that gets an array, indexes and a comparator and sorts the array
     * by creating 2 temp array, sorting them and then merging.
     */
    public static void merge(File[] arr, int l, int m, int r, Comparator<File> comparator )
    {
        int subArray1Size = m - l + 1;
        int subArray2Size = r - m;
        /* Create temp arrays */
        File[] tempLeftArray = new File[subArray1Size] ;
        File[] tempRightArray = new File [subArray2Size];
        /*Copy data to temp arrays*/
        for (int i=0; i<subArray1Size; i++){
            tempLeftArray[i] = arr[l + i];
        }
        for (int j=0; j<subArray2Size; j++) {
            tempRightArray[j] = arr[m + 1 + j];
        }
        /* Merge the temp arrays */
        int i = 0;
        int j = 0;
        int k = l;
        while (i < subArray1Size && j < subArray2Size)
        {
            if (comparator.compare(tempLeftArray[i],tempRightArray[j]) <0)
            {
                arr[k] = tempLeftArray[i];
                i++;
            }
            else if (comparator.compare(tempLeftArray[i],tempRightArray[j]) >0)
            {
                arr[k] = tempRightArray[j];
                j++;
            }
            k++;
        }
        /* Copy remaining elements of tempLeftArray[] if any */
        while (i < subArray1Size)
        {
            arr[k] = tempLeftArray[i];
            i++;
            k++;
        }
        /* Copy remaining elements of tempRightArray[] if any */
        while (j < subArray2Size)
        {
            arr[k] = tempRightArray[j];
            j++;
            k++;
        }
    }

    /**
     * A method that sorts a given array using merge() and a given comparator.
     */
    public static void sort(File[] array, int left, int right,Comparator<File> comparator)
    {
        if (left < right)
        {
            int middle = (left+right)/2;
            sort(array, left, middle,comparator);
            sort(array , middle+1, right,comparator);
            merge(array, left, middle, right,comparator);
        }
    }

    /**
     * A method that gets array of files and order it using a comparator and sort method.
     * @return sorted array of strings
     */
    public abstract ArrayList<String> orderFiles(ArrayList<File> filteredFilesList);

}
