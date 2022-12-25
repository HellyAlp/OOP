package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that their suffix equals to the value given .
 */
public class SuffixFilter extends Filter {
    private String fileSuffix;
    private ArrayList<File> filteredFilesList;

    public SuffixFilter(String suffix, boolean isNot) {
        super(isNot);
        fileSuffix = suffix;

    }

    /**
     * A method that filters the files given according to the filter conditions.
     * @return array of filtered files.
     */
    @Override
    public ArrayList<File> filteredFilesList(ArrayList<File> filesList) {
        filteredFilesList = new ArrayList<File>();
        for(File file:filesList){
            if (not) {
                if (!file.getName().endsWith(fileSuffix)) {
                    filteredFilesList.add(file);
                }
            }
            else{
                if(file.getName().endsWith(fileSuffix)) {
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
