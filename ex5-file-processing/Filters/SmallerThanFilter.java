package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that their size is smaller than the value given.
 */
public class SmallerThanFilter extends Filter{
    private double val;
    private ArrayList<File> filteredFilesList;
    public SmallerThanFilter(boolean isNot, String value){
        super(isNot);
        val = Double.parseDouble(value);
    }

    /**
     * A method that filters the files given according to the filter conditions.
     * @return array of filtered files.
     */
    @Override
    public ArrayList<File> filteredFilesList( ArrayList<File> filesList) {
        filteredFilesList = new ArrayList<File>();
        for(File file:filesList){
            double fileSize = bytesToKB(file.length());
            if (not) {
                if (fileSize >= val) {
                    filteredFilesList.add(file);
                }
            }
            else{
                if(fileSize <= val) {
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }


}
