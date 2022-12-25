/**
 * A class that implements Closed hash set.
 */
public class ClosedHashSet extends SimpleHashSet {
    private float upperLoad;
    private float lowerLoad;
    private Object[] hashTable;
    private final int ZERO =0;
    private final int ONE =1;
    private final int TWO = 2;
    private int curSize =ZERO;



    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        upperLoad = upperLoadFactor;
        lowerLoad = lowerLoadFactor;
        hashTable = new Object[INITIAL_CAPACITY] ;
    }

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load factor
     * (0.75) and lower load factor (0.25).
     */
    public  ClosedHashSet(){
        upperLoad = DEFAULT_HIGHER_CAPACITY;
        lowerLoad = DEFAULT_LOWER_CAPACITY;
        hashTable = new Object[INITIAL_CAPACITY] ;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75), and lower
     * load factor (0.25).
     * @param data  Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        upperLoad = DEFAULT_HIGHER_CAPACITY;
        lowerLoad = DEFAULT_LOWER_CAPACITY;
        hashTable = new Object[INITIAL_CAPACITY] ;
        for (String value : data){
            this.add(value);
        }
    }


    /**
     * A method that increases the hash table (twice the current size) by creating a new one and adding all the values
     * from the original to it.
     */
    private void increaseTable(){
        Object[] tempArray = hashTable;
        int curCapacity= capacity();
        hashTable = new Object[TWO*curCapacity] ;
        for (Object object:tempArray){
            if(object!=null && object.getClass() == String.class ){
                this.addObject(object.toString());
            }
        }
    }

    /**
     * A method that decreases the hash table (half the current size) by creating a new one and adding all the values
     * from the original to it.
     */
    private void decreaseTable(){
        Object[] tempArray = hashTable;
        hashTable = new Object[capacity()/TWO] ;

        for (Object object:tempArray){
            if(object!=null && object.getClass() == String.class ){
                addObject(object.toString());
            }
        }

    }

    /**
     * A function that do the calculation of probing value.
     * @param e string we want to add
     * @param i the index
     * @return int after calculating and clamping.
     */
    private int probingVal(String e,int i){
        int val;
        val = e.hashCode() + (i+i*i)/2;
        return this.clamp(val);
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
            return index & (capacity()-ONE);
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
     * A method that adds a specific value to the hash table.
     * @param addValue the string we want to add.
     */
    private void addObject(java.lang.String addValue){
        for(int i=0; i< hashTable.length;i++) {
            int index = probingVal(addValue, i);
            if(hashTable[index]== null || hashTable[index].getClass() == Integer.class) {
                hashTable[probingVal(addValue, i)] = addValue;
                break;
            }
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set, True otherwise.
     */
    @Override
    public boolean add(java.lang.String newValue) {
        if (contains(newValue)){
            return false;
        }
        else{
            if(size() + ONE <= getUpperLoadFactor()*capacity()){
                addObject(newValue);
                curSize = curSize +ONE;
                return true;
            }
            else {
                increaseTable();
                addObject(newValue);
                curSize = curSize +ONE;
                return true;
            }
        }

    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    @Override
    public boolean contains(java.lang.String searchVal) {
        int i =ZERO;
        if(hashTable[probingVal(searchVal, i)] == null ){
            return false;
        }
        else {
            for(i=0;i<hashTable.length;i++) {
                int index = probingVal(searchVal, i);
                if(this.hashTable[index]==null){
                    return false;
                }
                else if(this.hashTable[index]!=null &&this.hashTable[index].getClass() == String.class&&
                        this.hashTable[index].toString().equals(searchVal)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(java.lang.String toDelete) {
        int flag = ZERO;
        if(toDelete!=null && contains(toDelete)) {
            if (size() -ONE  < getLowerLoadFactor()*capacity()) {
                decreaseTable();
            }
            for(int i=0;i<hashTable.length;i++) {
                int index = probingVal(toDelete, i);
                if(this.hashTable[index]!=null &&this.hashTable[index].getClass() == String.class&&
                        this.hashTable[index].toString().equals(toDelete)) {
                    hashTable[index] = flag;
                    curSize = curSize -ONE;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return curSize;
    }

}
