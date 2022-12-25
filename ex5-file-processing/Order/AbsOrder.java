package filesprocessing.Order;
import java.io.File;
import java.util.ArrayList;

/**
 * A class that contains methods that order files by their absolute path.
 */
public class AbsOrder extends Order{
    private static boolean isReversed;
    public AbsOrder(boolean reversed) {
        super(reversed);
        isReversed = reversed;
    }

    /**
     * A method that gets array of files and order it using a comparator and sort method.
     * @return sorted array of strings
     */
    @Override
    public ArrayList<String> orderFiles(ArrayList<File> filteredFilesList) {
        ArrayList<String> orderedFileList = new ArrayList<String>();
        AbsComparator comparator = new AbsComparator();
        File[] filteredFilesArray = new  File[filteredFilesList.size()];
        int i = 0;
        for(File file:filteredFilesList){
            filteredFilesArray[i] = file;
            i++;
        }
        if (isReversed){
            sort(filteredFilesArray,0,filteredFilesList.size()-1,comparator.reversed());
        }
        else{
            sort(filteredFilesArray,0,filteredFilesList.size()-1,comparator);
        }
        for(File file: filteredFilesArray){
            String fileName = file.getName();
            orderedFileList.add(fileName);
        }
        return orderedFileList;
    }
}
