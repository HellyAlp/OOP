package filesprocessing.Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * An abstract class representing a filter.
 */
public abstract class Filter {
    protected boolean not;
    private final double BYTES_TO_KB = 1024;
    public Filter(boolean isNot){
        not = isNot;
    }

    /**
     * A method that filters the files given according to the filter conditions.
     * @return array of filtered files.
     */
    public abstract ArrayList<File> filteredFilesList(ArrayList<File> filesList);

    protected double bytesToKB(double bytes){
        return bytes/BYTES_TO_KB;

    }
}
