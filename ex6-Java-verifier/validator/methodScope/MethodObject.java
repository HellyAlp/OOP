package oop.ex6.validator.methodScope;

import oop.ex6.validator.PatternSearch;
import oop.ex6.validator.TypeChecks.AllTypeCheck;
import oop.ex6.validator.TypeChecks.IllegalTypeException;
import oop.ex6.validator.ValidationException;

import java.util.*;

/**
 * This class is the method object class. It creates a method object and initialize the name, lines and
 * parameters of the methods.
 * There is also the validation function for validation of each method in the javac file.
 */
public class MethodObject extends PatternSearch  {

    /** class magic numbers and strings */
    private static final String FINAL_STATEMENT = "final";

    /** Optional Types List */
    private static ArrayList<String> optionalTypes = new ArrayList<>(Arrays.asList("int", "double",
            "boolean", "char", "String"));

    /** The function code lines array*/
    private ArrayList<String> functionArray ;

    /** The function parameters*/
    private String[] parameters;

    /** The method name param*/
    private String methodName;

    /** The function parameters hash*/
    private LinkedHashMap<String,String> localVariables;

    /** The function parameters hash - each params have name and type save in the hashMap*/
    private LinkedHashMap<String,String> parameterInfo;

    /** The local final vars array*/
    private ArrayList<String> localFinalVars = new ArrayList<>();

    /** An AllTypeCheck object*/
    private AllTypeCheck allType;

    /**
     * The constructor of Method object. It gets the arrayList of the method scope lines
     * and the allType object and initialized them to local parameters.
     * @param array the arrayList of the method scope lines
     * @param allTypeCheck an AllTypeCheck object
     * @throws ValidationException
     * @throws IllegalTypeException
     */
    public MethodObject(ArrayList<String> array, AllTypeCheck allTypeCheck)
            throws ValidationException, IllegalTypeException {
        allType = allTypeCheck;
        functionArray = array;
        methodNameCheck(functionArray.get(0));
        parameters = MethodValidation.unpackParameters(functionArray.get(0),",");
        localVariables = new LinkedHashMap<>();
        parameterInfo  = new LinkedHashMap<>();
        if (!(parameters.length==1&&parameters[0].equals("")))
            // if there are parameters in the method call, it checks the format of the params
            parameterValidation();
        methodName = splitByRegex(functionArray.get(0),"([a-zA-Z][\\w]*|_[\\w]+)").get(1);
    }


    /**
     * if there are parameters in the method call, it checks the format of the params - the number
     * of commas and the validation of the type and name of the variables.
     * @throws ValidationException
     * @throws IllegalTypeException
     */
    private void parameterValidation() throws ValidationException, IllegalTypeException {
        String countComma = functionArray.get(0);
        int count = countComma.length() - countComma.replace(",", "").length();
        for(String parameter: this.parameters){
            // split the params list to each param without spaces
            ArrayList<String> splitedParam = new ArrayList<>();
            StringTokenizer paramSplit = new StringTokenizer(parameter);
            while (paramSplit.hasMoreTokens()){
                splitedParam.add(paramSplit.nextToken());
            }
            if (splitedParam.size()>0){
                // check if the parameter is final
                if(!splitedParam.get(0).equals(FINAL_STATEMENT) &&
                        !optionalTypes.contains(splitedParam.get(0))){
                    throw new ValidationException("parameter is not in the right format");
                }
                if (splitedParam.get(0).equals(FINAL_STATEMENT)){
                    parameter = finalParamsCase(splitedParam);
                }
                HashMap<String,String> tempVars;
                // validate the params if they contains "="
                if (!parameter.contains("=")) {
                    tempVars = allType.checkTypeValidation(parameter + ";", localVariables, true);
                    for (Map.Entry<String, String> entry : tempVars.entrySet()) {
                        setLocalVariables(entry.getKey(), entry.getValue());
                        parameterInfo.put(entry.getKey(), entry.getValue());
                        allType.initializedVars.add(entry.getKey());
                    }
                }
                else {
                    throw new ValidationException("Methods parameter is not in the right format");
                }
            }
        }
        if (count !=0 && parameterInfo.size()  != count +1){ //checks the numbers of cammas
            throw new ValidationException("too much commas in the parameter line");
        }
    }

    /**
     * Helper function who checks if the parameter is in the right format, in aspect of
     * number of word and valid type.
     * @param splitedParam the param for check splited to word in an array.
     * @return the edited params as string
     * @throws ValidationException
     */
    private String finalParamsCase(ArrayList<String> splitedParam)
            throws ValidationException {
        String param;
        try{
            localFinalVars.add(splitedParam.get(2));
            StringBuilder parameterBuilder = new StringBuilder();
            for (int i = 1; i<splitedParam.size(); i++){
                if(!optionalTypes.contains(splitedParam.get(1))){
                    throw new ValidationException("parameter is not in the right format");
                }
                parameterBuilder.append(splitedParam.get(i)).append(" ");
            }
            param = parameterBuilder.toString();
        }
        catch (IndexOutOfBoundsException | ValidationException e){
            throw new ValidationException("parameter is not in the right format");
        }
        return param;
    }


    /**
     * This function check with regex the method name correction.
     * @param line the line contains the method name
     * @throws ValidationException
     */
    private static void methodNameCheck(String line) throws ValidationException {
        patternSearch(line,
                "\\s*(void)\\s+[a-zA-Z]+[\\w]*\\s*[(].*[)]\\s*[{]\\s*$",
                "Method syntax is not valid",true);
    }


    /**
     * Get function array.
     * @return the ArrayList of method lines from the javac file.
     */
    public ArrayList<String> getFunctionArray(){
        return this.functionArray;
    }

    /**
     * Get local variable array.
     * @return local variable array.
     */
    public LinkedHashMap<String,String> getLocalVariables(){
        return localVariables;
    }

    /**
     * this function sets the local variables array, by adding new variable and type.
     * @param variable the new global variable
     * @param type the type of the variable
     */
    public void setLocalVariables( String variable, String type) {
        localVariables.put(variable,type);
    }

    /**
     * Get method name
     * @return method string name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets parameter LinkedHashMap info (variable and type)
     * @return parameter LinkedHashMap info
     */
    public LinkedHashMap<String, String> getParameterInfo() {
        return parameterInfo;
    }

    /**
     * Gets local final vars array.
     * @return local final vars array
     */
    public ArrayList<String> getLocalFinalVars() {
        return localFinalVars;
    }

    /**
     * this function sets the local final variables array, by adding new variable and type.
     * @param variable the new global variable
     */
    public void setLocalFinalVars(String variable) {
        localFinalVars.add(variable);
    }
}


