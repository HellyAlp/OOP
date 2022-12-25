/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to be able to
 * check out books, if a copy of the requested book is available.
 */

public class Library {
    /**
     * The maximal number of books this library can hold.
     */
    final int bookCapacity;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    final int maxBorrowed;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    int patronCapacity;

    final int NO_BOOKS = 0;
    final int ADD_BOOK = 1;
    final int ERROR_MEG = -1;
    int currentBooksNum = NO_BOOKS;
    int currentPatronsNum = NO_BOOKS;

    Book[] booksArray;
    Patron[] patronsArray;



    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given characteristic.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow
     *                          at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */

    Library(int maxBookCapacity,
            int maxBorrowedBooks,
            int maxPatronCapacity) {
        //your code goes here
        bookCapacity = maxBookCapacity;
        maxBorrowed = maxBorrowedBooks;
        patronCapacity = maxPatronCapacity;
        booksArray = new Book[bookCapacity];
        patronsArray = new Patron[patronCapacity];
    }

    /*----=  Instance Methods  =-----*/


    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        if (getBookId(book) == ERROR_MEG) {
            for (int i = 0; i < booksArray.length; i++) {
                if (booksArray[i] == null || booksArray[i] == book) {
                    currentBooksNum++;
                    booksArray[i] = book;
                    return i;
                }
            }
            return ERROR_MEG;
        }
        return getBookId(book);
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId - The id to check.
     * @returntrue if the given number is an id of some book in the library, false otherwise.
     */


    boolean isBookIdValid(int bookId) {
        if ((bookId >= NO_BOOKS) && (bookId <= booksArray.length)) {
            return (booksArray[bookId] != null);
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book- The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */

    int getBookId(Book book) {
        for (int i = 0; i < booksArray.length; i++) {
            if (booksArray[i] == book) {
                return i;
            }
        }
        return ERROR_MEG;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId - The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */

    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId)) {
            return booksArray[bookId].getCurrentBorrowerId() == ERROR_MEG;
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        if (getPatronId(patron) == ERROR_MEG) {
            for (int i = 0; i < patronsArray.length; i++) {
                if (patronsArray[i] == null) {
                    currentPatronsNum++;
                    patronsArray[i] = patron;
                    return i;
                }
            }
            //}
            return ERROR_MEG;
        }
        return getPatronId(patron);
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if ((patronId >= NO_BOOKS) && (patronId <= patronsArray.length) && patronsArray[patronId] != null) {
            return true;
        }
        return false;

    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron The patron for which to find the id number.
     * @return non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < patronsArray.length; i++) {
            if (patronsArray[i] == patron) {
                return i;
            }
        }
        return ERROR_MEG;
    }

    /**
     * A method that checks how many books a given patron has already borrowed.
     *
     * @param patron The patron for which to find how many books he borrowed.
     * @return int- the number of books.
     */

    int numBooksBorrowed(Patron patron) {
        int num = NO_BOOKS;
        for (int i = 0; i < booksArray.length; i++) {
            if (booksArray[i] != null && booksArray[i].currentBorrowerId == getPatronId(patron)) {
                num++;
            }
        }
        return num;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is
     * available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */

    boolean borrowBook(int bookId, int patronId) {
        if (isBookIdValid(bookId) && isPatronIdValid(patronId)) {
            Book wantedBook = booksArray[bookId];
            Patron wantedPatron = patronsArray[patronId];
            int patronBooksNum = numBooksBorrowed(wantedPatron);
            if (isBookAvailable(bookId) && (patronBooksNum++ < maxBorrowed) &&
                    wantedPatron.willEnjoyBook(wantedBook)) {
                wantedBook.currentBorrowerId = patronId;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId) && booksArray[bookId] != null) {
            if (booksArray[bookId].currentBorrowerId >= NO_BOOKS) {
                booksArray[bookId].returnBook();
            }

        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books
     * he will enjoy, if any such exist.
     *
     * @param patronId patronId - The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is available.
     */

    Book suggestBookToPatron(int patronId) {
        if (isPatronIdValid(patronId) && patronsArray[patronId] != null) {
            Book bestBook = booksArray[NO_BOOKS];
            int changes = NO_BOOKS;
            for (int i = 0; i < booksArray.length; i++) {
                if ((booksArray[i] != null) && (booksArray[i].getLiteraryValue() >= bestBook.getLiteraryValue())
                        && (patronsArray[patronId].willEnjoyBook(booksArray[i]))
                        && isBookAvailable(getBookId(booksArray[i]))) {
                    bestBook = booksArray[i];
                    changes++;
                }
            }
            if ((changes == NO_BOOKS) || (bestBook.currentBorrowerId != ERROR_MEG)) {
                return null;
            }
            return bestBook;
        }
        return null;

    }

}



