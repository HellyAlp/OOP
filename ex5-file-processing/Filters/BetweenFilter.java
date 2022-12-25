package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that their size is between the 2 values given.
 */
public class BetweenFilter extends Filter {
    private double val1;
    private double val2;


    public BetweenFilter(boolean isNot, String value1, String value2){
        super(isNot);
        val1 = Double.parseDouble(value1);
        val2 = Double.parseDouble(value2);
    }


    /**
     * A method that filters the files given according to the filter conditions.
     * @return array of filtered files.
     */
    @Override
    public ArrayList<File> filteredFilesList(ArrayList<File> filesList) {
        ArrayList<File> filteredFilesList = new ArrayList<File>();
        for(File file:filesList){
            double fileSize = bytesToKB(file.length());
            if (not) {
                if (fileSize < val1 || fileSize > val2) {
                    filteredFilesList.add(file);
                }
            }
            else{
                if(fileSize >= val1 && fileSize <= val2) {
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
