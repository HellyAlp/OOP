package filesprocessing.Order;

import java.io.File;
import java.util.Comparator;

/**
 * A class that implements comparator compering 2 files by their type .
 */
public class TypeComparator implements Comparator<File> {
    private static final String POINT = ".";

    /**
     * A method that gets a file extension (in order to get its type).
     */
    private String getType(String fileName){
        String type ="";
        if(fileName.lastIndexOf(POINT) != 0 && fileName.lastIndexOf(POINT)!= -1){
            type = fileName.substring((fileName.lastIndexOf(POINT)+1));
        }
        return type;
    }

    @Override
    public int compare(File file1, File file2) {
        String file1Name = file1.getName();
        String file2Name =file2.getName();
        if(getType(file1Name).equals(getType(file2Name))){
            String file1AbsolutePath = file1.getAbsolutePath();
            String file2AbsolutePath =file2.getAbsolutePath();
            return file1AbsolutePath.compareTo(file2AbsolutePath);
        }
        else {
            return getType(file1Name).compareTo(getType(file2Name));
        }
    }


}
