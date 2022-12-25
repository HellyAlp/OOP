/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet extends java.lang.Object implements SimpleSet {

    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    protected static final int INITIAL_CAPACITY = 16;
    private float upperLoad;
    private float lowerLoad;
    private Object[] hashTable;



    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY
     */
    public SimpleHashSet(){
        hashTable = new Object[INITIAL_CAPACITY] ;
        upperLoad = DEFAULT_HIGHER_CAPACITY;
        lowerLoad = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactory the upper load factor before rehashing
     * @param lowerLoadFactory the lower load factor before rehashing
     */
    public SimpleHashSet(float upperLoadFactory, float lowerLoadFactory){
        hashTable = new Object[INITIAL_CAPACITY] ;
        upperLoad = upperLoadFactory;
        lowerLoad = lowerLoadFactory;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index  the index before clamping
     * @return an index properly clamped
     */
    protected abstract int clamp(int index);

    /**
     * @return The lower load factor of the table.
     */
    protected abstract float getLowerLoadFactor();

    /**
     * @return The higher load factor of the table.
     */
    protected abstract float getUpperLoadFactor();


}
