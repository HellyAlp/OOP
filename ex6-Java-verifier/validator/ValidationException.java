package oop.ex6.validator;

/**
 * The ValidationException class.
 */
public class ValidationException extends Exception {

    /**class constance */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the class, whenever ValidationException thrown, it get the massage and
     * throw new ValidationException with the massage showing the problem.
     * @param massage the massage showing the problem raised the exception
     */
    public ValidationException(String massage) {
        super(massage);
    }
}




