package oop.ex6.validator.methodScope;

import oop.ex6.parsing.Parser;
import oop.ex6.validator.CodeValidation;
import oop.ex6.validator.GeneralChecks.AllGeneralChecks;
import oop.ex6.validator.GeneralChecks.IllegalContentException;
import oop.ex6.validator.PatternSearch;
import oop.ex6.validator.TypeChecks.IllegalTypeException;
import oop.ex6.validator.ValidationException;
import oop.ex6.validator.globalScope.GlobalValidation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the methods validation. It runs all the checks needed to validate a method.
 */
public class MethodValidation extends PatternSearch implements CodeValidation {

    /** class magic numbers and strings */
    private static final int KEY_WORD_TYPE1 = 1;
    private static final int KEY_WORD_TYPE2 = 2;
    private static final int KEY_WORD_TYPE3 = 3;
    private static final int KEY_WORD_TYPE4 = 4;
    private static final int KEY_WORD_TYPE5 = 5;
    private static final int KEY_WORD_TYPE6 = 6;
    private static final int KEY_WORD_TYPE7 = 7;
    private static final String FINAL_STATEMENT = "final";
    private static final String RETURN_STATEMENT = "return";
    private static final String IF_STATEMENT = "if";
    private static final String WHILE_STATEMENT = "while";



    /** Optional Types List */
    private static ArrayList<String> optionalTypes = new ArrayList<>(Arrays.asList("int", "double",
            "boolean", "char", "String"));

    /** The block index array - used when if/while block is initialized*/
    private ArrayList<Integer> blockIndex;

    /** The block file - if the checked line is inside or outside a block */
    private boolean blockFlag;

    /** The copied global var array*/
    private LinkedHashMap<String, String> tempGlobal;

    /** The copied local var array*/
    private LinkedHashMap<String, String> tempLocal;

    /** The copied final var array*/
    private ArrayList<String> tempFinal;

    /** The GlobalValidation object*/
    private GlobalValidation globalScope;

    /**
     * The class constructor of MethodValidation object. It gets the arrayList of the method lines
     * and the GlobalValidation object and initialized them to local parameters.
     * @param methodToCheck the method line
     * @param valid the GlobalValidation
     * @throws ValidationException
     */
    public MethodValidation(MethodObject methodToCheck,GlobalValidation valid) throws ValidationException {
         blockIndex = new ArrayList<>();
         blockFlag = false;
         tempGlobal= new LinkedHashMap<>();
         tempLocal = new LinkedHashMap<>();
         tempFinal = new ArrayList<>();
         globalScope = valid;
        validationCheck(methodToCheck);
    }

    /**
     * Helper function who gets a line and return the parames written in it split by the input regex.
     * @param line line for check
     * @return the type written in the line
     */
    public static String[] unpackParameters(String line,String reg)  {
        Pattern p = Pattern.compile("[(].*[)]");
        String str = line;
        Matcher m = p.matcher(str);
        m.find();
        return str.substring(m.start() + 1,m.end() - 1).split(reg);
    }

    /**
     * The main validation check method. It runs all the relevant methods who validate this method.
     * It gets the method object and check it correctness. There are some nested if to validate every
     * formats options
     * @param method a method object
     * @throws ValidationException
     */
    private void validationCheck(MethodObject method) throws ValidationException {
        int localInitIndex= globalScope.allTypeCheck.initializedVars.size();
        boolean flag = true;
        HashMap<String,String> tempVars;
        int i = 1;
        ArrayList<String> lines = method.getFunctionArray();
        while (i < lines.size()) {
            String line = lines.get(i);
            try {
                AllGeneralChecks.OperatorsAndArrayLineCheck(line);
                if (AllGeneralChecks.lineCheck(line)) {
                    if (line.endsWith("}")){
                        endMethodOperation();
                    }
                    else {
                        int keywordType = keywordValidation(line, method);
                        if (keywordType == KEY_WORD_TYPE5) {
                            methodCallCase(method, line);
                        } else if (keywordType == KEY_WORD_TYPE6) {
                            patternSearch(line, "^\\s*(return)[\\s]*;$",
                                    "Return statement is incorrect", true);
                        } else if (keywordType == KEY_WORD_TYPE7) {
                            blockValidation(line, method);
                        }
                        else {
                            if (keywordType == KEY_WORD_TYPE3) {
                                flag = false;
                                line = insertionCase(method.getLocalVariables(), line);
                            }
                            if (keywordType == KEY_WORD_TYPE4) {
                                flag = false;
                                line = insertionCase(globalScope.getGlobalVars(), line);
                            }
                            tempVars = blockCase(method, line, flag, keywordType);
                            addLocalVars(tempVars,method,keywordType);
                        }
                    }
            }
                } catch(ValidationException | IllegalTypeException | IllegalContentException e){
                    System.err.println(e.getMessage());
                    throw new ValidationException(e.getMessage());
                }
                i++;
        }
        while (localInitIndex < globalScope.allTypeCheck.initializedVars.size()){
            globalScope.allTypeCheck.initializedVars.remove(globalScope.allTypeCheck.initializedVars.
                    get(globalScope.allTypeCheck.initializedVars.size()-1));
        }
    }

    /**
     * Helper function who check a method call line.
     * @param method the checked method
     * @param line the calling function line
     * @throws ValidationException
     * @throws IllegalTypeException
     */
    private void methodCallCase(MethodObject method, String line)
            throws ValidationException, IllegalTypeException {
        String[] params = unpackParameters(line, ",");
        MethodObject excitingMethod = methodExistingCheck(getThePattern(line,
                "([a-zA-Z][\\w]*|_[\\w]+)"));
        if (!(params.length == 1 && params[0].equals("") && excitingMethod.getParameterInfo().size()==0)){
            if (params.length != excitingMethod.getParameterInfo().size()) {
                throw new ValidationException("method call is not valid");
            }
        }
        int j = 0;
        for (Map.Entry<String, String> entry : excitingMethod.getParameterInfo().entrySet()) {
            String parameterLine = entry.getValue() + " " + entry.getKey() + " = " + params[j] + ";";
            if(blockFlag){
                globalScope.allTypeCheck.checkTypeValidation(parameterLine, tempLocal, false);

            }
            else {
                globalScope.allTypeCheck.checkTypeValidation(parameterLine, method.getLocalVariables(),
                        false);
            }
            j++;
        }
    }

    /**
     * Helper function who add the local vars to the temp local array
     * @param method the checked method
     * @param keywordType the keywordType for final variables
     */
    private void addLocalVars(HashMap<String,String> tempVars, MethodObject method, int keywordType){
        for (Map.Entry<String, String> entry : tempVars.entrySet()) {
            if (!blockFlag) {
                method.setLocalVariables(entry.getKey(), entry.getValue());
                if (keywordType == KEY_WORD_TYPE2) {
                    method.setLocalFinalVars(entry.getKey());
                }
            } else {
                tempLocal.put(entry.getKey(), entry.getValue());
                if (keywordType == KEY_WORD_TYPE2) {
                    tempFinal.add(entry.getKey());
                }
            }
        }
    }

    /**
     * Helper function for block case. It add all the local temp to the global temp whenever there is
     * nested loops or if.
     * @param method the checked method
     * @param line the calling function line
     * @param flag true if the line is in block and false otherwise
     * @return the new variable who checked and needed to be added
     * @throws IllegalTypeException
     * @throws ValidationException
     */
    private HashMap<String, String> blockCase(MethodObject method, String line, boolean flag, int keywordType)
            throws IllegalTypeException, ValidationException {
        if (blockFlag){
            HashMap<String, String> temp = new HashMap<>();
            temp.putAll(tempLocal);
            temp.putAll(tempGlobal);
            return globalScope.allTypeCheck.checkTypeValidation(line, temp,true);
        }
        else{
            HashMap<String, String> temp = new HashMap<>();
            if(keywordType == KEY_WORD_TYPE1 && !line.contains("=")){
                temp.putAll(method.getLocalVariables());
            }
            else {
                temp.putAll(globalScope.getGlobalVars());
                temp.putAll(method.getLocalVariables());
            }
            return globalScope.allTypeCheck.checkTypeValidation(line, temp, flag);
        }
    }

    /**
     * This function deals with the insertion new variable Case in a method.
     * @param variables the variable needed to be insert
     * @param line the corrent checked line
     * @return the variable in the format - type nameVar = variable
     * @throws ValidationException
     */
    private String insertionCase(HashMap<String,String> variables, String line) throws ValidationException {
        String firstString = getThePattern(line, "([a-zA-Z][\\w]*|_[\\w]+)");
        String type = variables.get(firstString);
        return type + " " + line;
    }

    /**
     * Helper function who do all the actions needed in the end of a method
     */
    private void endMethodOperation(){
        int index = blockIndex.get(blockIndex.size()-1);
        while (index > 0){
            tempGlobal.remove(tempGlobal.entrySet().toArray()[tempGlobal.size()-1]);
            index--;
        }
        if (blockIndex.size()==1){
            blockFlag =false;
            tempLocal.clear();
            tempFinal.clear();
        }
    }

    /**
     * This helping function check if a calling to existing method is correct.
     * @param methodName the method call
     * @return MethodObject that being called
     */
    public  MethodObject methodExistingCheck(String methodName){
        String[] fullName = methodName.split("[(]");
        for (MethodObject methodToCheck:Parser.getAllMethods()){
            if (fullName[0].equals(methodToCheck.getMethodName())){
                return methodToCheck;
            }
        }
        return null;
    }

    /**
     * This methods splits the first pattern in the line and classified it to categories.
     * @param line the line in the method that being checked now.
     * @param method that being checks
     * @return the key number according to the first word in line
     * @throws ValidationException
     */
    public  int keywordValidation(String line,MethodObject method) throws ValidationException {
        String firstString = getThePattern(line,"([a-zA-Z][\\w]*|_[\\w]+)");
        if (optionalTypes.contains(firstString)){
            return KEY_WORD_TYPE1;
        }
        else if (firstString.equals(FINAL_STATEMENT)){
            return KEY_WORD_TYPE2;
        }
        else if (method.getLocalVariables().containsKey(firstString)&&(!method.getLocalFinalVars().
                contains(firstString))){
            return KEY_WORD_TYPE3;
        }
        else if (globalScope.getGlobalVars().containsKey(firstString)&&(!globalScope.getGlobalFinalVars().
                contains(firstString))){
            return KEY_WORD_TYPE4;
        }
        else if(methodExistingCheck(firstString)!= null){
            return KEY_WORD_TYPE5;
        }
        else if(line.contains(RETURN_STATEMENT)){
            return 6;
        }
        else if(firstString.contains(IF_STATEMENT)||firstString.contains(WHILE_STATEMENT)){
            return KEY_WORD_TYPE7;
        }
        else{
            throw new ValidationException("variable type is not valid");
        }
    }

    /**
     * Helper function who checks if the current block is valid
     * @param line the line in the method that being checked now.
     * @param method that being checks
     * @throws ValidationException
     * @throws IllegalTypeException
     */
    private void blockValidation(String line, MethodObject method) throws ValidationException,
            IllegalTypeException {
        patternSearch(line,
                "(\\s*(if|while)\\s*[(](\\s*[-]?[\\w.-]+\\s*([|]{2}|[&]{2})?)+[)]\\s*[{])\\s*$",
                "block is not in the right format",true);
        String[] blockConditions = unpackParameters(line,"[|]{2}|[&]{2}");
        ArrayList<String> conditions = new ArrayList<>();
        for (String condition : blockConditions){
            conditions.add(getThePattern(condition,"\\w*[^ ]"));
        }
        if(!blockFlag){
            tempGlobal.putAll(globalScope.getGlobalVars());
            tempGlobal.putAll(method.getLocalVariables());
            blockFlag = true;
        }
        else {
            tempGlobal.putAll(tempLocal);

        }
        blockIndex.add(tempGlobal.size());
        for (String condition : conditions){
            String fullCondition = "boolean tempName =" + condition + ";";
            globalScope.allTypeCheck.checkTypeValidation(fullCondition, tempGlobal,false);
        }
    }
}


