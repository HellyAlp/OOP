package filesprocessing.Parsing;
import java.io.*;
import java.util.ArrayList;

/**
 * A class that is inside the Parsing package that has methods that parses a given file.
 */
public class ParsingModule {
    private  static FileReader commandFile;
    private static BufferedReader  reader;
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String SEPARATOR = "#";
    private static final String ERROR = "ERROR: ";
    private static final String ABS = "abs";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final String ORDER_ERROR = "ORDER sub-section missing";
    private static final String FILTER_ERROR = "FILTER sub-section missing";
    private static final String SECTION_ERROR = "the section format is not good.";


    /**
     * A method that gets all data from file and puts every line in a string array.
     * @param filePath a path of the commands file.
     * @return a string array.
     */
    private static ArrayList<String> createFileContent(String filePath) {
        try {
            ArrayList<String> fileContent = new ArrayList<String>();
            commandFile = new FileReader(filePath);
            reader = new BufferedReader(commandFile);
            String line = reader.readLine();
            while (line != null) {
                fileContent.add(line);
                line = reader.readLine();
            }
            return fileContent;
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: the file given is not good.");
            return null;
        } catch (IOException e) {
            System.err.println("ERROR: the file given is not good.");
            return null;
        }
    }

    /**
     * The main parsing method, tht gets a file path and parses the texts in the file.
     * @param filePath a path of the commands file.
     * @return string array of string arrays, each inner array represents a section.
     */
    public static ArrayList<ArrayList<String>> parseGeneralData(String filePath) {
        try {
            ArrayList<String> commandLines = createFileContent(filePath);

            ArrayList<ArrayList<String>> totalSectionsArray = new ArrayList<ArrayList<String>>();
            if (commandLines.size() == 0){
                throw new NoFilterException();
            }
            if (commandLines.size() < THREE){
                throw new SectionFormatException();
            }
            int i =0;
            while (i < commandLines.size() - ONE) {
                ArrayList<String> sectionArray = new ArrayList<String>();
                if (!commandLines.get(i).equals(FILTER)) {
                    throw new NoFilterException();
                } else {
                    sectionArray.add(commandLines.get(i+ONE));
                    sectionArray.add(String.valueOf(i+TWO));
                }
                if (i+2>commandLines.size() -ONE|| !commandLines.get(i + TWO).equals(ORDER)) {
                    throw new NoOrderException();
                }
                i = i + THREE;
                if (i>commandLines.size() -ONE||commandLines.get(i).equals(FILTER)) {
                    if (i == commandLines.size() -ONE){
                        throw new SectionFormatException();
                    }
                    sectionArray.add(ABS);
                    sectionArray.add(String.valueOf(i+ONE));
                } else {
                    sectionArray.add(commandLines.get(i));
                    sectionArray.add(String.valueOf(i+ONE));
                    i++;
                }
                if(sectionArray.size()!=FOUR){
                    throw new SectionFormatException();
                }
                totalSectionsArray.add(sectionArray);
            }
            return totalSectionsArray;
        } catch (NoFilterException e) {
            System.err.println(ERROR+FILTER_ERROR );
            return null;
        }
        catch (NoOrderException e){
            System.err.println(ERROR+ORDER_ERROR);
            return null;
        }
        catch (SectionFormatException e){
            System.err.println(ERROR+SECTION_ERROR);
            return null;
        }

    }

    /**
     * A method that parses the filter line to filter name and values.
     * @param filterFullName the line after FILTER containing all the filter vars.
     */
    public static String[] parseFilterData(String filterFullName){
        return filterFullName.split(SEPARATOR);
    }

    /**
     * A method that parses the order line to order name and values.
     * @param orderFullName the line after ORDER containing all the filter vars.
     */
    public static String[] parseOrderData(String orderFullName){
        return orderFullName.split(SEPARATOR);
    }

}
