package oop.ex6.validator.globalScope;

import oop.ex6.validator.CodeValidation;
import oop.ex6.validator.PatternSearch;
import oop.ex6.validator.TypeChecks.AllTypeCheck;
import oop.ex6.validator.TypeChecks.IllegalTypeException;
import oop.ex6.validator.ValidationException;

import java.util.*;

/**
 * This is the GlobalValidation class.
 * In this class, some global scope are preformed such as format validation for initialized variable
 * or add value to existing value.
 */
public class GlobalValidation extends PatternSearch implements CodeValidation {

    /** class magic numbers and strings */
    private static final int KEY_WORD_TYPE1 = 1;
    private static final int KEY_WORD_TYPE2 = 2;
    private static final int KEY_WORD_TYPE3 = 3;
    private static final String FINAL_STATEMENT = "final";

    /** Optional Types List */
    private static ArrayList<String> optionalTypes = new ArrayList<>(Arrays.asList("int", "double",
            "boolean", "char", "String"));

    /** Global variables List */
    private HashMap<String, String> globalVars;

    /** Global final variables List*/
    private ArrayList<String> globalFinalVars;

    /** An AllTypeCheck object*/
    public AllTypeCheck allTypeCheck;

    /**
     * The constructor of GlobalValidation object. It gets the arrayList of the global scope lines
     * ans the allType object and initialized them to local parameters.
     * @param globalScope the arrayList of the global scope lines
     * @param allType an AllTypeCheck object
     * @throws ValidationException
     */
    public GlobalValidation(ArrayList<String> globalScope, AllTypeCheck allType) throws ValidationException{
        globalVars = new HashMap<>();
        globalFinalVars = new ArrayList<>();
        allTypeCheck = allType;
        this.validationCheck(globalScope);
    }

    /**
     * This function runs all the needed checks to approve a global scope of a file.
     * @param lines the lines in the global scope
     * @throws ValidationException
     */
    public void validationCheck(ArrayList<String> lines) throws ValidationException {
        boolean flag = true;
        HashMap<String,String> tempVars;
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            try {
                int keywordType = keywordValidation(line);
                if (keywordType == KEY_WORD_TYPE3){
                    flag= false;
                    String firstString = getThePattern(line,"([a-zA-Z][\\w]*|_[\\w]+)");
                    String type = getGlobalVars().get(firstString);
                    line = type+" "+line;
                }
                tempVars = allTypeCheck.checkTypeValidation(line,getGlobalVars(),flag);
                for(Map.Entry<String, String> entry:tempVars.entrySet()){
                    setGlobalVars(entry.getKey(),entry.getValue());
                    if (keywordType == KEY_WORD_TYPE2) {
                        setGlobalFinalVars(entry.getKey());
                    }
                }
            } catch (ValidationException | IllegalTypeException e) {
                System.err.println(e.getMessage());
                throw new ValidationException(e.getMessage());
            }
            i++;
        }
    }

    /**
     * This methods splits the first pattern in the line and classified it to categories.
     * @param line the line in the global scope beeing checked now.
     * @return the key number according to the first word in line
     * @throws ValidationException
     */
    private int keywordValidation(String line) throws ValidationException {
        try {
            String firstString = getThePattern(line, "([a-zA-Z][\\w]*|_[\\w]+)");
            if (optionalTypes.contains(firstString)){
                return KEY_WORD_TYPE1;
            }
            else if (firstString.equals(FINAL_STATEMENT)){
                return KEY_WORD_TYPE2;
            }
            else if (globalVars.containsKey(firstString)&&(!globalFinalVars.contains(firstString))){
                return KEY_WORD_TYPE3;
            }
            else{
                throw new ValidationException("variable type is not valid");
            }
        }catch (Exception e){
            throw new ValidationException("invalid syntax");
        }
    }

    /**
     * This function return the global vars hashMap
     * @return the global vars hashMap
     */
    public HashMap<String, String> getGlobalVars() {
        return globalVars;
    }

    /**
     * This function sets the global vars hashMap
     * @param variable the new global variable
     * @param type the type of the variable
     */
    public void setGlobalVars( String variable, String type) {
        globalVars.put(variable,type);
    }

    /**
     * This function return the global final vars hashMap
     * @return the global final vars hashMap
     */
    public  ArrayList<String> getGlobalFinalVars() {
        return globalFinalVars;
    }

    /**
     * This function sets the global final vars hashMap
     * @param variable the new global variable that is final
     */
    public void setGlobalFinalVars( String variable) {
        this.globalFinalVars.add(variable);
    }
}
