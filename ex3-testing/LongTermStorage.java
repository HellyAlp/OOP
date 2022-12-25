import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 *A class that represents long term storage object.
 */
public class LongTermStorage  {
    private int ACTION_DONE_SUCCESSFULLY = 0;
    private int ZERO = 0;
    private int NUMBER_OF_ITEMS_ERROR = -1;
    private int LTS_CAPACITY = 1000;
    private int ltsCapacity;
    private Map<String,Integer> inventory;
    private int takenCapacity;

    /**
     *  constructor creating the long term storage object.
     */
    public LongTermStorage() {
        inventory = new HashMap<String, Integer>();
        ltsCapacity = LTS_CAPACITY;
        takenCapacity = ZERO;
    }

    public int addItem(Item item, int n){
        if ( n > ZERO) {
            if (getAvailableCapacity() - n * item.getVolume() >= ZERO) {
                for (String key : inventory.keySet()) {
                    if (key.equals(item.getType())) {
                        int curItemCount = getItemCount(item.getType());
                        inventory.put(key, n + curItemCount);
                        int curTakenCapacity = takenCapacity;
                        takenCapacity = curTakenCapacity + (n * item.getVolume());
                        return ACTION_DONE_SUCCESSFULLY;
                    }
                }
                inventory.put(item.getType(), n);
                int curTakenCapacity = takenCapacity;
                takenCapacity = curTakenCapacity + (n * item.getVolume());
                return ACTION_DONE_SUCCESSFULLY;
            } else {
                System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                        + n + " items of type " + item.getType());
                return NUMBER_OF_ITEMS_ERROR;
            }
        }
        System.out.println(" Error: Your request cannot be completed at this time. ");
        return NUMBER_OF_ITEMS_ERROR;
    }


    /**
     * This method resets the long-term storage’s inventory.
     */
    public void resetInventory(){
        inventory = getInventory();
        inventory= new HashMap<String, Integer>();
        takenCapacity= ZERO;
    }


    /**
     * This method returns the number of Items of type type
     * the long-term storage contains
     */
    public int getItemCount(String type){
        if(inventory.containsKey(type)){
            return inventory.get(type);
        }
        return ZERO;
    }

    /**
     * This method returns a map of all the Items
     * contained in the long-term storage unit, and their respective quantities.
     */
    public Map<String,Integer> getInventory(){ return inventory; }

    /**
     * Returns the long-term storage’s total capacity.
     */
    public int getCapacity(){ return ltsCapacity;}

    /**
     * Returns the long-term storage’s available capacity, i.e. how many storage units are unoccupied by Items
     */
    public int getAvailableCapacity(){return (ltsCapacity - takenCapacity); }


}
