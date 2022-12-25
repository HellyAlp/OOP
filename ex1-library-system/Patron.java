/**
 * This class represents a book, which has a title, author, year of publication and different literary aspects.
 */
public class Patron {

    /**
     * The first name of the patron.
     */
    final String firstName;

    /**
     * The last name of the patron.
     */
    final String lastName;

    /**
     * The weight the patron assigns to the comic aspects of books..
     */
    int comicPreference;

    /**
     * The weight the patron assigns to the dramatic aspects of books.
     */
    int dramaticPreference;

    /**
     * The weight the patron assigns to the educational aspects of books
     */
    int educationalPreference;

    /**
     * The minimal literary value a book must have for this patron to enjoy it.
     */
    int enjoymentThreshold;
    final int NO_BOOKS = 0;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristic.
     *
     * @param patronFirstName          The first name of the patron
     * @param patronLastName           The last name of the patron.
     * @param comicTendency            The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency         The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency      The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy it.
     */

    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {
        //your code goes here
        firstName = patronFirstName;
        lastName = patronLastName;
        comicPreference = comicTendency;
        dramaticPreference = dramaticTendency;
        educationalPreference = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;


    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * @return Returns the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        int score = comicPreference * book.comicValue + dramaticPreference * book.dramaticValue +
                educationalPreference * book.educationalValue;
        return score;
    }

    /**
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) > enjoymentThreshold;
    }
}