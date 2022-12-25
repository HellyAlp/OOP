package filesprocessing.Filters;
import java.io.File;
import java.util.ArrayList;

/**
 * A class representing a filter that do not filter the files.
 */
public class AllFilter extends Filter {
    private  ArrayList<File> filteredFilesList;
    public AllFilter(boolean isNot) {
        super(isNot);
    }

    /**
     * A method that filters the files given according to the filter conditions.
     * @return array of filtered files.
     */
    @Override
    public ArrayList<File> filteredFilesList(ArrayList<File> filesList) {
        filteredFilesList = filesList;
        return filteredFilesList;
    }
}
