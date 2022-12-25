package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files the executable or not according to the value given.
 */
public class ExecutableFilter extends Filter {
    private String isExecutable;
    private final String YES = "YES";
    private final String NO = "NO";
    private ArrayList<File> filteredFilesList;

    public ExecutableFilter(String val, boolean isNot) {
        super(isNot);
        isExecutable = val;

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
                if (isExecutable.equals(YES) && !file.canExecute()) {
                    filteredFilesList.add(file);
                }
                else if(isExecutable.equals(NO) && file.canExecute()){
                    filteredFilesList.add(file);
                }
            }
            else{
                if(isExecutable.equals(YES) && file.canExecute()) {
                    filteredFilesList.add(file);
                }
                else if(isExecutable.equals(NO) && !file.canExecute()){
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
