package oop.ex6.validator.TypeChecks;

/**
 * Class of IllegalTypeException.
 */
public class IllegalTypeException extends Exception {

        /** IllegalTypeException constance */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor of the class, whenever IllegalTypeException thrown, it get the massage and
         * throw new IllegalTypeException with the massage showing the problem.
         * @param massage the massage showing the problem raised the exception
         */
        public IllegalTypeException(String massage) {
            super(massage);
        }
}


