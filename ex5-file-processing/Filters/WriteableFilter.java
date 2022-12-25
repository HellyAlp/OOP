package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files the writeable or not according to the value given.
 */
public class WriteableFilter extends Filter {
    private String isWriteable;
    private final String YES = "YES";
    private final String NO = "NO";
    private ArrayList<File> filteredFilesList;

    public WriteableFilter(String val, boolean isNot) {
        super(isNot);
        isWriteable = val;

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
                if (isWriteable.equals(YES) && !file.canWrite()) {
                    filteredFilesList.add(file);
                }
                else if(isWriteable.equals(NO) && file.canWrite()){
                    filteredFilesList.add(file);
                }
            }
            else{
                if(isWriteable.equals(YES) && file.canWrite()) {
                    filteredFilesList.add(file);
                }
                else if(isWriteable.equals(NO) && !file.canWrite()){
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
