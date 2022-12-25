package oop.ex6.validator.GeneralChecks;
/**
 * Class of IllegalContentException.
 */
public class IllegalContentException extends Exception {

        /** exception constant*/
        private static final long serialVersionUID = 1L;

        /**
         * Constructor of the class, whenever IllegalContentException thrown, it get the massage and
         * throw new IllegalContentException with the massage showing the problem.
         * @param massage the massage showing the problem raised the exception
         */
        public IllegalContentException(String massage) {
            super(massage);
        }
}


