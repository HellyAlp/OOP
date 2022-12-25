import oop.ex3.spaceship.*;

/**
 *A class that represents Spaceship object.
 */
public class Spaceship {
    private int[] crewIdList;
    private int lockersNum;
    private String spaceShipName;
    private Locker[] lockers;
    private int ACTION_DONE_SUCCESSFULLY = 0;
    private int ZERO = 0;
    private int ID_ERROR = -1;
    private int CAPACITY_ERROR = -2;
    private int MAX_LOCKERS_ERROR = -3;
    private LongTermStorage lts;
    private int CurNumberOfLockers;


    /**
     * constructor creating the Spaceship object.
     * @param name name of the spaceship.
     * @param crewIDs list of the ids of the crew members on board.
     * @param numOfLockers number of the maximum lockers on the spaceship.
     */
    public Spaceship(String name, int[] crewIDs,int numOfLockers){
        spaceShipName = name;
        crewIdList = crewIDs;
        lockersNum = numOfLockers;
        CurNumberOfLockers = ZERO;
        lockers = new Locker[lockersNum];
        lts =new LongTermStorage();
    }

    /**
     * This method returns the long-term storage object associated with that Spaceship.
     */
    public LongTermStorage getLongTermStorage(){ return lts; }

    /**
     * This method creates a Locker object and adds it as part of the Spaceship's storage.
     * The new locker is associated with a crew member.
     * @param crewID A crew members id that the locker is associated with.
     * @param capacity The capacity the locker can obtain.
     * @return 0 if the operation has been successful , -1 if the id is not valid, -2 if ths capacity does not meet
     * the locker class requirements and -3 if the spaceship already contains the allowed number of lockers.
     */
    public int createLocker(int crewID, int capacity){
        if(capacity < ZERO){
            return CAPACITY_ERROR;
        }
        for(int i=0;i<crewIdList.length; i++) {
            if (CurNumberOfLockers < lockersNum) {
                if (crewIdList[i] == crewID) {
                    Locker newLocker = new Locker(getLongTermStorage(), capacity, ItemFactory.getConstraintPairs());
                    for (int j = 0; j < lockers.length; j++) {
                        if (lockers[j] == null) {
                            lockers[j] = newLocker;
                            CurNumberOfLockers++;
                            return ACTION_DONE_SUCCESSFULLY;
                        }
                    }
                }
            }
            else {
                return MAX_LOCKERS_ERROR;
            }
        }
        return ID_ERROR;
    }

    /**
     * This methods returns an array with the crew’s ids.
     */
    public int[] getCrewIDs(){ return crewIdList; }

    /**
     * This methods returns an array of the Lockers.
     */
    public Locker[] getLockers(){ return lockers; }
}
