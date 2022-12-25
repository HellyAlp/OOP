package oop.ex6.main;

import oop.ex6.parsing.Parser;
import oop.ex6.parsing.ParsingException;
import oop.ex6.validator.TypeChecks.AllTypeCheck;
import oop.ex6.validator.ValidationException;
import oop.ex6.validator.globalScope.GlobalValidation;
import oop.ex6.validator.methodScope.MethodValidation;

import java.io.IOException;

/**
 * This class is the manager class of the Sjavac commandsFiles.
 * It calls all the relevant methods and classes to make the correct checking according to the
 * Sjavac principles.
 */
public class Sjavac {

    /** class magic numbers and strings */
    private static final String SUCCESS_MASSAGE = "0";
    private static final String GENERAL_ERROR_MASSAGE = "1";
    private static final String IO_ERROR_MASSAGE = "2";


    /**
     * The main function. It calls all the relevant methods and
     * classes to make the correct checking according to the Sjavac principles.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        try {
            String fileName;
            if (args.length == 1) { // check if the args in the right size
                fileName = args[0];
            } else {
                throw new IOException();
            }
            // create the file as an array
            AllTypeCheck allTypeCheck = new AllTypeCheck();
            // split the file to global scope and methods
            Parser parser = new Parser(fileName, allTypeCheck);
            GlobalValidation valid = new GlobalValidation(parser.getGlobalScopeContent(), allTypeCheck);
            for(int i=0;i<parser.getAllMethods().size(); i++){
                new MethodValidation(parser.getAllMethods().get(i),valid);
            }
            System.out.println(SUCCESS_MASSAGE);

        } catch (ValidationException | ParsingException e) {
            System.out.println(GENERAL_ERROR_MASSAGE);
        } catch (IOException e) {
            System.out.println(IO_ERROR_MASSAGE);
        }
    }
}
