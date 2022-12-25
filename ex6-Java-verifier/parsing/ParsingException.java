package oop.ex6.parsing;

/**
 * This is the Parsing exception class. It called if there is an ParsingException.
 */
public class ParsingException extends Exception {

    /** Exception constance */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the class, whenever IllegalContentException thrown, it get the massage and
     * throw new IllegalContentException with the massage showing the problem.
     */
    public ParsingException() {
        super();
    }

}
