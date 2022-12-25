package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that their prefix equals to the value given .
 */
public class PrefixFilter extends Filter {
    private String filePrefix;
    private ArrayList<File> filteredFilesList;

    public PrefixFilter(String prefix, boolean isNot) {
        super(isNot);
        filePrefix = prefix;

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
                if (!file.getName().startsWith(filePrefix)) {
                    filteredFilesList.add(file);
                }
            }
            else{
                if(file.getName().startsWith(filePrefix)) {
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
