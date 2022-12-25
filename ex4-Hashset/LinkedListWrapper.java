import java.util.LinkedList;

/**
 * A wrapper class for linked list objects for OpenHashSet class;
 */
public class LinkedListWrapper {
    private LinkedList<String> linkedList;

    /**
     * this class constructor.
     */
    public LinkedListWrapper(){
        linkedList = new LinkedList<String>();
    }

    /**
     * A nethod that returns the linkedList object
     * @return
     */
    public LinkedList<String> getLinkedList() {
        return linkedList;
    }
}
