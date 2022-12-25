package oop.ex6.validator;

import java.util.ArrayList;

/**
 * the code validation interface, it have two methods, the validationCheck and keywordValidation
 * that implement in the inheritance classes
 */
public interface CodeValidation {


    /**
     * This function runs all the needed checks to approve a global scope of a file.
     * @param lines the lines in the global scope
     */
    public static void validationCheck(ArrayList<String> lines) {
    }

    /**
     * This methods splits the first pattern in the line and classified it to categories.
     * @param line the line in the global scope being checked now.
     * @return the key number according to the first word in line
     * @throws ValidationException
     */
    public static int keywordValidation(String line) throws ValidationException{
        return 0;
    }


}
