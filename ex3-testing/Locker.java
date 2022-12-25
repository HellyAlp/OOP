import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import java.util.HashMap;
import java.util.Map;

/**
 *A class that represents locker object.
 */
public class Locker {
    private int ACTION_DONE_SUCCESSFULLY = 0;
    private int ZERO = 0;
    private int NUMBER_OF_ITEMS_ERROR = -1;
    private int ITEM_WENT_TO_LTS = 1;
    private int CONSTRAINTS_ERROR = -2;
    private double HALF_OF_LOCKER_CAPACITY = 0.5;
    private double MAX_ITEMS_IN_LOCKER = 0.2;
    private LongTermStorage longTermUnit;
    private int lockerCapacity;
    private Item[][] constraintsList;
    private Map<String,Integer> inventory;
    private int takenCapacity;


    /***
     * This constructor initializes a Locker object that is associated with the given long-term storage
     * with the given capacity and Item constraints.
     * @param lts long-term storage  associated with the locker.
     * @param capacity the number of storage space this locker have.
     * @param constraints 2d array list of pairs of items that cannot be stored together.
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints){
        longTermUnit = lts;
        lockerCapacity = capacity;
        takenCapacity = ZERO;
        constraintsList = constraints;
        inventory = new HashMap<String, Integer>();
    }


    /**
     * This method checks if a given item is part of the constraint pairs.
     * @param item the item we want to check.
     * @return true if this item is part of the constraint pairs, false if not.
     */
    private boolean checkConstraints(Item item) {
        constraintsList = ItemFactory.getConstraintPairs();
        for (int i = 0; i < constraintsList.length; i++) {
            for (int j = 0; j < constraintsList[i].length; j++) {
                if (constraintsList[i][j].getType().equals(item.getType())) {
                    if (j == 0) {
                        String pairedItemType = constraintsList[i][j + 1].getType();
                        if (getInventory().containsKey(pairedItemType) && getItemCount(pairedItemType) >ZERO) {
                            return true;
                        }
                    } else if (j == 1) {
                        String pairedItemType = constraintsList[i][j - 1].getType();
                        if (getInventory().containsKey(pairedItemType) && getItemCount(pairedItemType) >ZERO) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * This method checks if items from the same type takes more than 50% of the total locker capacity.
     * @return -1 if the items from the same type takes more than 50% of the total locker capacity,or a int that we
     * can move to lts in order to keep that items from the same type takes 20% of the total locker capacity.
     */
    private int checkItemCapacity(Item item,int n) {
        int i = NUMBER_OF_ITEMS_ERROR;
        int itemCount = getItemCount(item.getType()) +n;
        int itemStorageUnit = item.getVolume();
        int totalItemCapacity = itemCount * itemStorageUnit;
        if (totalItemCapacity > Math.floor(HALF_OF_LOCKER_CAPACITY * getCapacity())){
            for( i=0; i < totalItemCapacity; i++){
                if ((totalItemCapacity-i) < Math.floor(MAX_ITEMS_IN_LOCKER*getCapacity())){
                    return (int) Math.ceil(i /itemStorageUnit);
                }
            }
        }
        return i;
    }


    /**
     *This method adds n Items of the given type to the locker.
     * @param item the item we want to add to the inventory.
     * @param n the number of items we want to add to the inventory.
     * @return
     */
    public int addItem(Item item,int n) {
        if(n > ZERO){
            if (!checkConstraints(item)) {
                if (getAvailableCapacity() - n*item.getVolume() >= ZERO) {
                    if (checkItemCapacity(item,n) > ZERO) {
                        double newN = checkItemCapacity(item,n);
                        if (longTermUnit.addItem(item, (int)newN) > NUMBER_OF_ITEMS_ERROR) {
                            int itemsToRemove = (int)newN - n;
                            if(itemsToRemove > ZERO){
                                removeItem(item,itemsToRemove);
                            }
                            else{
                                int addN = Math.abs(itemsToRemove);
                                if(getInventory().containsKey(item.getType())){
                                    int currentVal = getInventory().get(item.getType());
                                }
                                int currentVal = ZERO;
                                getInventory().put(item.getType(), currentVal + addN-1);
                                int currentTakenCapacity = takenCapacity;
                                takenCapacity = currentTakenCapacity +( item.getVolume() * addN);
                            }
                            System.out.println("Warning: Action successful, but has caused items to be moved to " +
                                    " storage ");
                            return ITEM_WENT_TO_LTS;


                        }
                        System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                                + (int)newN + " items of type " + item.getType());
                        return NUMBER_OF_ITEMS_ERROR;
                    }
                    if (getInventory().containsKey(item.getType())) {
                        int currentVal = getInventory().get(item.getType());
                        getInventory().put(item.getType(), currentVal + n);
                        int currentTakenCapacity = takenCapacity;
                        takenCapacity = currentTakenCapacity +( item.getVolume() * n);
                    } else {
                        getInventory().put(item.getType(), n);
                        int currentTakenCapacity = takenCapacity;
                        takenCapacity = currentTakenCapacity +( item.getVolume() * n);
                    }
                    return ACTION_DONE_SUCCESSFULLY;
                }
                System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                        + n + " items of type " + item.getType());
                return NUMBER_OF_ITEMS_ERROR;
            }
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker cannot" +
                    " contain items of type " + item.getType() + " ,as it contains a contradicting item ");
            return CONSTRAINTS_ERROR;
        }
        System.out.println(" Error: Your request cannot be completed at this time. ");
        return NUMBER_OF_ITEMS_ERROR;
    }

    /***
     * This method removes n Items of the type type from the locker.
     * @param item the item we want to remove.
     * @param n the number of items we want to remove.
     * @return 0 if done successfully, -1 if the n is negative or n is bigger tan the number of items in the inventory.
     */
    public int removeItem(Item item, int n){
        if(getItemCount(item.getType()) >= n){
            if(n > ZERO){
                int itemCount = getItemCount(item.getType());
                int newItemCount = itemCount - n  ;
                getInventory().put(item.getType(),newItemCount);
                int currentTakenCapacity = takenCapacity;
                takenCapacity = currentTakenCapacity - (n*item.getVolume());
                return ACTION_DONE_SUCCESSFULLY;
            }
            if(n==ZERO){
                System.out.println(" Error: Your request cannot be completed at this time. ");
                return NUMBER_OF_ITEMS_ERROR;
            }
            System.out.println("Error: Your request cannot be completed at this time. Problem: cannot remove " +
                    " negative number of items of type " + item.getType());
            return NUMBER_OF_ITEMS_ERROR;
        }
        System.out.println("Error: Your request cannot be completed at this time. Problem: the locker does not contain "
                + n +" items of type "+ item.getType());
        return NUMBER_OF_ITEMS_ERROR;
    }


    /**
     * This method returns the number of Items of type type the locker contains.
     */
    public int getItemCount(String type){
        if(inventory.containsKey(type)){
            return inventory.get(type);
        }
        return ZERO;
    }

    /**
     *This method returns a map of all the item types contained in the locker, and their respective quantities.
     */
    public Map<String, Integer> getInventory(){return inventory;}

    /**
     * This method returns the locker’s total capacity.
     */
    public int getCapacity(){ return lockerCapacity;}

    /**
     * This method returns the locker’s available capacity, i.e. how many storage units are unoccupied by Items
     */
    public int getAvailableCapacity(){
        return (lockerCapacity - takenCapacity);
    }
}
