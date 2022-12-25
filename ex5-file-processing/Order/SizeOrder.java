package filesprocessing.Order;
import java.io.File;
import java.util.ArrayList;

/**
 * A class that contains methods that order files by their size.
 */
public class SizeOrder extends Order {

    private static boolean isReversed;
    public SizeOrder(boolean reversed) {
        super(reversed);
        isReversed = reversed;
    }

    /**
     * A method that gets array of files and order it using a comparator and sort method.
     * @return sorted array of strings
     */
    public ArrayList<String> orderFiles(ArrayList<File> filteredFilesList){
        ArrayList<String> orderedFileList = new ArrayList<String>();
        SizeComparator comparator = new SizeComparator();
        File[] filteredFilesArray = new  File[filteredFilesList.size()];
        int i = 0;
        for(File file:filteredFilesList){
            filteredFilesArray[i] = file;
            i++;
        }
        if (isReversed){
            sort(filteredFilesArray,0,filteredFilesList.size()-1,comparator.reversed());
        }
        else {
            sort(filteredFilesArray,0,filteredFilesList.size()-1,comparator);
        }
        for(File file: filteredFilesArray){
            orderedFileList.add(file.getName());
        }
        return orderedFileList;
    }
}
