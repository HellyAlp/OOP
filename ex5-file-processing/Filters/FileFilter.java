package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that filter the files that named like the value given.
 */
public class FileFilter extends Filter {
    private String fileName;
    private ArrayList<File> filteredFilesList;

    public FileFilter(String file, boolean isNot) {
        super(isNot);
        fileName = file;
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
                if (!file.getName().equals(fileName)) {
                    filteredFilesList.add(file);
                }
            }
            else{
                if(fileName.equals(file.getName())) {
                    filteredFilesList.add(file);
                }
            }
        }
        return filteredFilesList;
    }
}
