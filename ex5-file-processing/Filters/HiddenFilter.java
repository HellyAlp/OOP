package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that hidden or not according to the value given.
 */
public class HiddenFilter extends Filter {
    private String isHidden;
    private final String YES = "YES";
    private final String NO = "NO";
    private ArrayList<File> filteredFilesList;

    public HiddenFilter(String val, boolean isNot) {
        super(isNot);
        isHidden = val;

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
                if (isHidden.equals(YES) && !file.isHidden()) {
                    filteredFilesList.add(file);
                }
                else if(isHidden.equals(NO) && file.isHidden()){
                    filteredFilesList.add(file);
                }
            }
            else{
                if(isHidden.equals(YES) && file.isHidden()) {
                    filteredFilesList.add(file);
                }
                else if(isHidden.equals(NO) && !file.isHidden()){
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
