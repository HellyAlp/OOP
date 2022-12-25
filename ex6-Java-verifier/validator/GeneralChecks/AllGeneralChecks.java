package oop.ex6.validator.GeneralChecks;
import oop.ex6.validator.PatternSearch;
import oop.ex6.validator.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The AllGeneralChecks class. It runs the general basic check for each line for first approve of the line.
 * The methods in this class checking if the line is one of the follow - empty line, comment line or code
 * line, and if so, if it ended with ;, { or, }.
 */
public class AllGeneralChecks extends PatternSearch {
    /**
     * This function checks if the line is one of the follow - empty line, comment line or code line.
     * If it is not one of the those conditions, it will throw IllegalContentException.
     * @param line the line to check
     * @throws IllegalContentException if the line is not in the right format.
     */
    public static boolean lineCheck(String line) throws IllegalContentException {
        try {
            if (line.startsWith("//")) { // comment check
                PatternSearch.patternSearch(line, "^[\\s]*$|.+//",
                        " comment is not in the right format",
                        false);
            } else { //empty line check
                Pattern p = Pattern.compile("^[\\s]*$");
                Matcher matcher = p.matcher(line);
                boolean match = matcher.find();
                if (match) {
                    return false;
                } else {
                    PatternSearch.patternSearch(line,
                            "^[\\s]*$|^\\s*[\\w =()'\".]+\\s*[^;]*\\s*;\\s*$|^[\\s]*}$|.+[{]\\s*$",
                            " line is not in the right format", true);
                    return true;
                }
            }
        }
        catch (ValidationException e){ // if this line isn't one of the correct formats
            throw new IllegalContentException(e.getMessage());
        }
        return false;
    }


    /**
     * This function checks if the line contains operators or arrays.
     * If it is, it will throw IllegalContentException.
     * @param line the line to check
     * @throws IllegalContentException if the line is not in the right format.
     * @param line the checked line
     * @throws IllegalContentException
     */
    public static void OperatorsAndArrayLineCheck(String line) throws IllegalContentException {
        try {
            PatternSearch.patternSearch(line,"[^=]\\d+[ ]*[+]|[^=]\\d+[ ]*[-]",
                    "There is illegal operator in the javac"
                    ,false);
            PatternSearch.patternSearch(line,"[\\[]|[]]", "Arrays are illegal in javac"
                    ,false);
        }
        catch (ValidationException e){
            throw new IllegalContentException(e.getMessage());
        }
    }
}
