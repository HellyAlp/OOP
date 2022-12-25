package filesprocessing.Order;

import java.io.File;
import java.util.Comparator;

/**
 * A class that implements comparator compering 2 files by their size.
 */
public class SizeComparator implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        double file1Size = file1.length();
        double file2Size =file2.length();
        if(file1Size > file2Size){
            return 1;
        }
        else if (file1Size < file2Size){
            return -1;
        }
        else {
            return (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
        }
    }
}
