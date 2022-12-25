package oop.ex6.parsing;

import oop.ex6.validator.GeneralChecks.AllGeneralChecks;
import oop.ex6.validator.GeneralChecks.IllegalContentException;
import oop.ex6.validator.TypeChecks.AllTypeCheck;
import oop.ex6.validator.TypeChecks.IllegalTypeException;
import oop.ex6.validator.ValidationException;
import oop.ex6.validator.methodScope.MethodObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is the parsing class.
 * In this class, its get a file and first convert it to an array of String lines.
 * Than, it split it to global scope and methods object.
 */
public class Parser {

    /** class magic numbers and strings */
    private static final String ERROR_MASSAGE = "ERROR: ";
    private static final String RETURN_STATEMENT = "return";
    private static final String VOID_STATEMENT = "void";

    /**The array of lines */
    private ArrayList<String> linesArray;

    /** Methods List */
    private static ArrayList<MethodObject> allMethods;

    /** Global Scope lines */
    private  ArrayList<String> globalScopeContent;

    /** AllTypeCheck object */
    private AllTypeCheck allType;


    /**
     * Class constructor, get the file name and the AllTypeCheck object
     * and initialized all the relevant params.
     * @param fileName the name of the file for the file reader
     * @param allTypeCheck the AllTypeCheck object for future tasks
     * @throws ParsingException
     * @throws IOException
     */
    public Parser(String fileName, AllTypeCheck allTypeCheck) throws ParsingException, IOException {
        allType = allTypeCheck;
        allMethods  = new ArrayList<>();
        globalScopeContent = new ArrayList<>();
        parseFile(Objects.requireNonNull(fileReader(fileName)));
    }

    /**
     * A method that goes throw the file and saves its lines in an array.
     * @param file javac command file
     * @return String array containing the file lines.
     * @throws IOException
     */
    private ArrayList<String> fileReader(String file) throws IOException {
        Path path = Paths.get(file);
        int lineCounter = (int) Files.lines(path).count();
        linesArray = new ArrayList<>();
        BufferedReader lines = null;
        try {
            lines = new BufferedReader(new FileReader(file));
            for(int i = 0; i< lineCounter; i++){
                linesArray.add(lines.readLine());
            }
        } catch (FileNotFoundException e){
            System.err.println(ERROR_MASSAGE + "file: " + file + " is not found.");
            return null;
        } catch (IOException e){
            System.err.println(ERROR_MASSAGE + "IO error .");
            return null;
        } finally {
            try {
                if(lines != null) {
                    lines.close();
                } else {
                    return null;
                }
            } catch (IOException e) {
                System.err.println(ERROR_MASSAGE + "Could not close the file " + file + ".");
            }
        }
        return linesArray;
    }

    /**
     * parse the file method. It gets an arrayList of the file line and split it to global scope and
     * methods object.
     * In this methods there is some general validation checks.
     * @param file an arrayList of the file line
     * @throws ParsingException
     */
    private void parseFile(ArrayList<String> file) throws ParsingException {
        int i = 0;
        while (i < file.size()) {
            String line = file.get(i);
            try {
                // general format checks
                AllGeneralChecks.OperatorsAndArrayLineCheck(line);
                if (AllGeneralChecks.lineCheck(line)) {
                    //if the line is not empty or a comment - type checks
                    if (line.contains(VOID_STATEMENT))
                        i= packTheMethod(file,i) +1;
                    else {
                        this.globalScopeContent.add(line);
                        i++;
                    }
                }
                else i++;
            } catch (ValidationException | IllegalContentException | IllegalTypeException e) {
                System.err.println(e.getMessage());
                throw new ParsingException();
            }
        }
    }

    /**
     * A method that pack all the lines in a method format. It creates a method object.
     * @param file String array.
     * @param i index.
     * @return the index of the line ending the method.
     */
    private int packTheMethod(ArrayList<String> file, int i) throws ValidationException,IllegalTypeException{
        int counter1 = 1;
        int counter2 = 0;
        ArrayList<String> methodContent = new ArrayList<>();
        while (counter1 != counter2 && i < file.size() - 1) {
            methodContent.add(file.get(i));
            i++;
            if (file.get(i).contains("{")) {
                counter1++;
            } else if (file.get(i).contains("}")) {
                counter2++;
            }
        }
        if (counter1==counter2) {
            if (methodContent.get(methodContent.size() - 1).contains(RETURN_STATEMENT)) {
                allMethods.add(new MethodObject(methodContent, allType));
            }
            else{
                throw new ValidationException("return statement is not in the correct form");
            }
        }
        else {
            throw new ValidationException("the method in the file is not in the right format");
        }
        return i;
    }

    /**
     * Returns the global Scope content.
     * @return global Scope content
     */
    public ArrayList<String> getGlobalScopeContent(){
        return globalScopeContent;
    }

    /**
     * Returns the allMethods ArrayList.
     * @return allMethods ArrayList
     */
    public static ArrayList<MethodObject> getAllMethods() {
        return allMethods;
    }
}
