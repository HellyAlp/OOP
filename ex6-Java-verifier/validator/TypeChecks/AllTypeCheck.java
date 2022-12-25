package oop.ex6.validator.TypeChecks;

import oop.ex6.validator.PatternSearch;
import oop.ex6.validator.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * This class contains all the type checks.
 */
public class AllTypeCheck extends PatternSearch {

    /** class magic numbers and strings */
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final String STRING = "String";



    /** Initialized variables array */
    public  ArrayList<String> initializedVars;

    /**
     * Class constructorm it initialized the initializedVars array.
     */
    public AllTypeCheck(){
        initializedVars = new ArrayList<>();

    }

    /**
     * This function split the line and return the value
     * @param line the line to split
     * @param type the type of the value
     * @return
     * @throws IllegalTypeException
     */
    private String valueFormat(String line, String type) throws IllegalTypeException {
        String value;
        ArrayList<String> values = splitByRegex(line, "[\\s]*[\\w]+[^ ]*");
        if (values.size() != 1) {
            throw new IllegalTypeException(type + " syntax is not valid");
        }
        StringTokenizer paramSplit = new StringTokenizer(values.get(0));
        value = paramSplit.nextToken();
        return value;
    }


    /**
     * this function checked the int type var. It checks if the value after the "=" is in the right format
     * and throw exception if not.
     * @param line the line to check.
     * @throws IllegalTypeException
     */
    private void intCheck(String line, HashMap<String,String> initVars) throws IllegalTypeException {
        try { // checks if the value is int
            patternSearch(line, "[=][\\s]*[-]?[+]?\\d+[\\s]*;+$",
                    "int syntax is not valid", true);
        } catch (ValidationException e) {
            String value = valueFormat(line, INT);
            // checks if the value is an variable or illegal value
            if (!(initVars.containsKey(value) &&(initializedVars.contains(value))&&
                    initVars.get(value).equals(INT))){
                throw new IllegalTypeException("int syntax is not valid");
            }
        }
    }

    /**
     * this function checked the double type var. It checks if the value after the "=" is in the right format
     * and throw exception if not.
     * @param line the line to check.
     * @throws IllegalTypeException
     */
    private void doubleCheck(String line, HashMap<String,String> initVars) throws IllegalTypeException {
        try { // checks if the value is double
            patternSearch(line,"[=][\\s]*[-]?[+]?\\d+[.]?\\d*[\\s]*[;]\\s*$",
                    "double syntax is not valid", true);
        } catch (ValidationException e) {
            String value = valueFormat(line, DOUBLE);
            if (!(initVars.containsKey(value) &&(initializedVars.contains(value))&&
                    (initVars.get(value).equals(DOUBLE)
                    | initVars.get(value).equals(INT)))){
                throw new IllegalTypeException("double syntax is not valid");
            }
        }
    }

    /**
     * this function checked the boolean type var. It checks if the value after the "=" is in the right format
     * and throw exception if not.
     * @param line the line to check.
     * @throws IllegalTypeException
     */
    private  void booleanCheck(String line, HashMap<String,String> initVars) throws IllegalTypeException {
        try { // checks if the value is boolean
            patternSearch(line,
                    "[=][\\s]*((false)|(true)|([+]?[-]?)\\d*[.]*\\d*)[\\s]*;\\s*$",
                    "boolean syntax is not valid", true);
        } catch (ValidationException e) {
            // checks if the value is an variable or illegal value
            String value = valueFormat(line, BOOLEAN);
            // checks if the value is an variable or illegal value
            if (!((initVars.containsKey(value)) &&(initializedVars.contains(value))&&
                    (initVars.get(value).equals(BOOLEAN)||
                    initVars.get(value).equals(INT)
                    || initVars.get(value).equals(DOUBLE)))){
                throw new IllegalTypeException("boolean syntax is not valid");
            }
        }
    }

    /**
     * this function checked the char type var. It checks if the value after the "=" is in the right format
     * and throw exception if not.
     * @param line the line to check.
     * @throws IllegalTypeException
     */
    private void charCheck(String line, HashMap<String,String> initVars) throws IllegalTypeException {
        try { // checks if the value is char
            patternSearch(line,
                    "[=][\\s]*'+\\s*[\\d\\w\\W]?'+[\\s]*;$",
                    "char syntax is not valid", true);
        } catch (ValidationException e) {
            // checks if the value is an variable or illegal value
            String value = valueFormat(line, CHAR);
            // checks if the value is an variable or illegal value
            if (!(initVars.containsKey(value) &&(initializedVars.contains(value))&&
                    initVars.get(value).equals(CHAR))) {
                throw new IllegalTypeException("char syntax is not valid");
            }
        }
    }

    /**
     * this function checked the string type var. It checks if the value after the "=" is in the right format
     * and throw exception if not.
     * @param line the line to check.
     * @throws IllegalTypeException
     */
    private void stringCheck(String line, HashMap<String,String> initVars) throws IllegalTypeException {
        try { // checks if the value is String
            patternSearch(line,
                    "[=][\\s]*[\"]+(\\s)*[\\d\\w\\W]*[\"]+[\\s]*;$",
                    "String syntax is not valid", true);
        } catch (ValidationException e) {
            String value = valueFormat(line, STRING);
            // checks if the value is an variable or illegal value
            if (!(initVars.containsKey(value) &&(initializedVars.contains(value))&&
                    initVars.get(value).equals(STRING))){
                throw new IllegalTypeException("String syntax is not valid");
            }
        }
    }


    /**
     * This function check if the line of initialize a variable correct for all the approved types in
     * javac format.
     * @param args the args in the line
     * @param initVars the hashMap of the initialized params
     * @param flag true if we need to check for variable duplication, false if not
     * @return the updated tempVars
     * @throws IllegalTypeException
     * @throws ValidationException
     */
    public HashMap<String,String> checkTypeValidation(String args, HashMap<String,String> initVars,
                                                      boolean flag)
            throws IllegalTypeException, ValidationException {
        HashMap<String,String> tempVars = new  HashMap<>();
        String type = getThePattern(args, "(int|String|double|char|boolean)");
        ArrayList<String> lineStringArray = splitByComma(args,type);
        // check the name of the variable and the type
        for (String line:lineStringArray) {
            String var = firstPartCheck(line);
            String[] allVars = var.split(" ");
            String variable = allVars[allVars.length-1];
            //check if the line after the "=" is correct
            if (line.contains("=")) {
                variableInsertion(line, type, initVars);
                initializedVars.add(variable);
            }
                if (flag && (initVars.containsKey(variable)||tempVars.containsKey(variable))) {
                throw new IllegalTypeException("variable with the same name already exists");
            } else {
                tempVars.put(variable,type);
                initVars.put(variable,type);
            }
        }
        return tempVars;
    }

    /**
     * this function splits the params in the args by commas
     * @param args parameters
     * @param type parameters type
     * @return ArrayList of parameter splitted by commas
     */
    private ArrayList<String> splitByComma(String args, String type) throws IllegalTypeException {
        ArrayList<String> lineStringArray;
        String countComma = args;
        int count = countComma.length() - countComma.replace(",", "").length();
        if(args.contains(",")){
            lineStringArray = splitByRegex(args, "(\\w+[\\s*=\\s*]?[^,;]*)");
            lineStringArray.set(0,lineStringArray.get(0)+";");
            boolean isFinal = lineStringArray.get(0).contains("final");
            for(int i=1;i<lineStringArray.size();i++){
                if (isFinal){
                    lineStringArray.set(i,"final "+ type +" "+ lineStringArray.get(i)+";");
                }
                else {
                    lineStringArray.set(i,type +" "+ lineStringArray.get(i)+";");
                }
            }
        }
        else{
            String countEqual = args;
            int EqualCount = countEqual.length() - countEqual.replace("=", "").length();
            if (EqualCount > 1){
                throw new IllegalTypeException("there are to many =");
            }
            lineStringArray = new ArrayList<>();
            lineStringArray.add(args);
        }
        if (count !=0 && lineStringArray.size()  != count +1 ){
            throw new IllegalTypeException("commas");
        }
        return lineStringArray;
    }

    /**
     * This helper function checks the parameter until the "=" (the type, the var name, final)
     * @param line the checked line
     * @return the var extract from the line
     * @throws ValidationException
     */
    private String firstPartCheck(String line) throws ValidationException {
        String var;
        try {//check if the line until the "=" is correct
            patternSearch(line,
                    "^\\s*(final)?\\s*(int|String|double|char|boolean)[\\s]+([a-zA-Z]" +
                            "[\\w]*|_[\\w]+)[\\s]*[=]", "variable name is not valid",true);
            var = getThePattern(line,
                    "^\\s*(final)?\\s*(int|String|double|char|boolean)[\\s]+([a-zA-Z]" +
                            "[\\w]*|_[\\w]+)[\\s]*");

        } catch (ValidationException e) {
            patternSearch(line,
                    "^\\s*(int|String|double|char|boolean)[\\s]+([a-zA-Z]" +
                            "[\\w]*|_[\\w]+)[\\s]*[=|;]",
                    "variable name is not valid",true);
            var = getThePattern(line, "^\\s*(int|String|double|char|boolean)[\\s]+([a-zA-Z]" +
                    "[\\w]*|_[\\w]+)[\\s]*" );
        }
        return var;
    }

    /**
     * This helper function split by cases according to the type of the variable
     * @param line the line to check
     * @param type the type of the variable
     * @param initVars the vares the already initialized
     * @throws ValidationException
     * @throws IllegalTypeException
     */
    public void variableInsertion(String line, String type, HashMap<String, String> initVars)
            throws ValidationException, IllegalTypeException {
            String subString = getThePattern(line,"[=][\\s]*[\\d\\w\\W]*[\\s]*");
            switch (type) {
                case (INT):
                    intCheck(subString,initVars);
                    break;
                case (DOUBLE):
                    doubleCheck(subString,initVars);
                    break;
                case (BOOLEAN):
                    booleanCheck(subString,initVars);
                    break;
                case (CHAR):
                    charCheck(subString,initVars);
                    break;
                case (STRING):
                    stringCheck(subString,initVars);
                    break;
            }
        }

}
