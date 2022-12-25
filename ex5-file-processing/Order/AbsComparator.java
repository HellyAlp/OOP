package filesprocessing.Order;

import java.io.File;
import java.util.Comparator;

/**
 * A class that implements comparator compering 2 files by their absolute path.
 */
public class AbsComparator implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        String file1AbsolutePath = file1.getAbsolutePath();
        String file2AbsolutePath =file2.getAbsolutePath();
        return file1AbsolutePath.compareTo(file2AbsolutePath);
    }
}
