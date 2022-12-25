package oop.ex6.validator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is helper class for the classes in this exercise. It contains 3 methods who
 * search a pattern in an input string and return it.
 */
public class PatternSearch {
    /**
     * This function checks if a pattern is match to the input line, and throws exception by the flag
     * @param line the line to check
     * @param pattern the pattern we search in the line
     * @param massage this massage will sent with the exception
     * @param flag true if we are looking for this pattern or false if we don't want it in the line.
     * @throws ValidationException if the line is not in the right format.
     */
    public static void patternSearch(String line, String pattern, String massage, boolean flag)
            throws ValidationException {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(line);
        boolean match = matcher.find();
        if(match & !flag){
            throw new ValidationException(massage);
        }
        else if(!match & flag){
            throw new ValidationException(massage);
        }
    }

    /**
     * Helper function who get a line and return the type written in it.
     * @param line line for check
     * @return the type written in the line
     */
    public static String getThePattern(String line, String pat) throws ValidationException{
        Pattern p = Pattern.compile(pat);
        String str = line;
        Matcher m = p.matcher(str);
        if (m.find()){
            return str.substring(m.start(),m.end());
        }
        throw new ValidationException("testing");
    }

    /**
     * this function get a line and a pattern, and split the line by it.
     */
    protected static ArrayList<String> splitByRegex(String line, String pat){
        ArrayList<String> temp = new ArrayList<>();
        Pattern p = Pattern.compile(pat);
        String str = line.replace(";", " ");
        Matcher m = p.matcher(str);
        while (m.find()){
            temp.add(str.substring(m.start(),m.end()));
        }
        return temp;
    }
}
