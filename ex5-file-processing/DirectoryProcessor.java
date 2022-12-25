package filesprocessing;
import filesprocessing.Filters.Filter;
import filesprocessing.Order.Order;
import filesprocessing.Parsing.ParsingModule;
import filesprocessing.Section.SectionModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class in the project. represent the program that gets a file and a directory and filters and orders the
 * files in the given directory according to the commands in the file.
 */
public class DirectoryProcessor {
    private static String filePath;
    private static String sourceDir;
    private static ArrayList<String> orderedList;
    private static ArrayList<File> filteredList;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int ZERO = 0;
    private static File directory;
    private static ArrayList<File> filesInDirectory;
    private static final String ERROR = "ERROR: ";
    private static final String ARGS_ERROR =" arguments entered is not valid";
    private static final String COMMAND_ERROR =" you didnt wrote the command right";


    /**
     * a constructor for the DirectoryProcessor class.
     */
    public DirectoryProcessor(String[] args) {
        try {
            if (args.length != TWO) {
                throw new InputException();
            } else {
                sourceDir = args[ZERO];
                filePath = args[ONE];
            }
        }
        catch (InputException e){
            System.err.println(ERROR+ARGS_ERROR);
        }
    }

    /**
     * A method that gets a directory and gets all the files inside it.
     * @return array of files.
     */
    private static ArrayList<File> createFilesInDir(String sourceDir) throws Exception {
            filesInDirectory = new ArrayList<File>();
            directory = new File(sourceDir);
            File[] tempFileList = directory.listFiles();
            if (tempFileList != null){
                for (File file : tempFileList) {
                    if (file.isFile()) {
                        filesInDirectory.add(file);
                    }
                }
            }
        return filesInDirectory;
    }

    public static void main(String[] args){
        try{
            if (args.length!= TWO){
                throw new InputException();
            }
            ArrayList<ArrayList<String>> sectionsArray = ParsingModule.parseGeneralData(args[1]);
            if (sectionsArray == null){
                return;
            }
            for(ArrayList<String> section:sectionsArray){
                String[] filterValues = ParsingModule.parseFilterData(section.get(ZERO));
                Filter newFilter = SectionModule.generateFilter(filterValues,Integer.parseInt(section.get(ONE)));
                filteredList = newFilter.filteredFilesList(createFilesInDir(args[ZERO]));
                String[] orderValues = ParsingModule.parseOrderData(section.get(TWO));
                Order newOrder = SectionModule.generateOrder(orderValues,Integer.parseInt(section.get(THREE)));
                orderedList = newOrder.orderFiles(filteredList);
                for(String fileName: orderedList){
                    System.out.println(fileName);
                }
            }
    } catch (InputException e) {
            System.err.println(ERROR+ARGS_ERROR);
        }
        catch (IOException e){
            System.err.println(ERROR+COMMAND_ERROR);
        } catch (Exception e) {
            //
        }
    }
}

