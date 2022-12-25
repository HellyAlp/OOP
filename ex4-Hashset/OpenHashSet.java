/**
 * A class that implements Open hash set.
 */
public class OpenHashSet extends SimpleHashSet {
    private final int ZERO =0;
    private final int ONE =1;
    private final double TWO =2;
    private final double HALF =0.5;
    private LinkedListWrapper[] hashTable;
    private float upperLoad;
    private float lowerLoad;
    private int curSize;
    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor  The upper load factor of the hash table.
     * @param lowerLoadFactor  The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        upperLoad = upperLoadFactor;
        lowerLoad = lowerLoadFactor;
        curSize =ZERO;
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        upperLoad = DEFAULT_HIGHER_CAPACITY;
        lowerLoad = DEFAULT_LOWER_CAPACITY;
        curSize =ZERO;


    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        upperLoad = DEFAULT_HIGHER_CAPACITY;
        lowerLoad = DEFAULT_LOWER_CAPACITY;
        curSize =ZERO;
        for(String value:data){
            this.add(value);
        }
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return hashTable.length;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index  the index before clamping
     * @return an index properly clamped
     */
    @Override
    protected int clamp(int index) {
            return index & (hashTable.length-1);
    }

    /**
     * @return The lower load factor of the table.
     */
    @Override
    protected float getLowerLoadFactor() {
        return lowerLoad;
    }

    /**
     * @return The higher load factor of the table.
     */
    @Override
    protected float getUpperLoadFactor() {
        return upperLoad;
    }


    /**
     * A method that resize the hash table (if increase needed-twice the current size,
     * if decrease needed-half the current size) by creating a new hash table and adding all the values
     * from the original to it.
     */
    private void resizeTable(double tableNewSize){
        LinkedListWrapper[] tempArray = hashTable;
        hashTable = new LinkedListWrapper[(int) ( tempArray.length*tableNewSize)];
        for (LinkedListWrapper linkedListWrapper:tempArray){
            if(linkedListWrapper!=null){
                for(int i =0; i< linkedListWrapper.getLinkedList().size();i++){
                    int index = clamp(linkedListWrapper.getLinkedList().get(i).hashCode());
                    if(hashTable[index] == null){
                        hashTable[index] = new LinkedListWrapper();
                    }
                    hashTable[index].getLinkedList().add(linkedListWrapper.getLinkedList().get(i));
                }
            }
        }

    }


    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set, True otherwise.
     */
    @Override
    public boolean add(String newValue) {
        if(contains(newValue)){
            return false;
        }
        else{
            if(size() + ONE > getUpperLoadFactor()*capacity()){
                resizeTable(TWO);
            }
            int index = clamp(newValue.hashCode());
            if(hashTable[index] == null){
                hashTable[index] = new LinkedListWrapper();
            }
            hashTable[index].getLinkedList().add(newValue);
            curSize = curSize +ONE;
            return true;


        }
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int index = clamp(searchVal.hashCode());
        if (hashTable[index]==null){
            return false;
        }
        else {
            return hashTable[index].getLinkedList().contains(searchVal);
        }
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if(contains(toDelete)){
                if(size() - ONE >= getLowerLoadFactor()*capacity()){
                    int index = clamp(toDelete.hashCode());
                    hashTable[index].getLinkedList().remove(toDelete);
                    curSize = size() - ONE;
                    return true;
                }
                else{
                    if(capacity() >ONE){
                        resizeTable(HALF);
                    }
                    int index = clamp(toDelete.hashCode());
                    hashTable[index].getLinkedList().remove(toDelete);
                    curSize = size() -ONE ;
                    return true;
                }
            }
        else {
            return false;
        }
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return curSize;
    }
}
