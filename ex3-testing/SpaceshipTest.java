import oop.ex3.spaceship.*;
import org.junit.*;
import java.util.Arrays;
import static org.junit.Assert.*;

/***
 * This Class contains test methods testing the Spacechip class and its methods.
 */
public class SpaceshipTest {
    private Spaceship testObject;
    private String testSpaceShipName;
    private int[] testCrewIdList;
    private int testNumOfLockers;

    /***
     * creating new Spaceship object before every test.
     */
    @Before
    public void creatObject(){
        testSpaceShipName = "bereshit";
        testCrewIdList = new int[]{1, 2,3,4, 5, 6};
        testNumOfLockers = 5;
        testObject = new Spaceship(testSpaceShipName,testCrewIdList,testNumOfLockers);

    }
    /**
     * Testing creating locker with legal arguments.
     */
    @Test
    public void creatLockerTest1(){
        assertEquals("problem with creating locker",0,testObject.createLocker(1,100));

    }

    /**
     * Testing crating locker with illegal crew id.
     */
    @Test
    public void creatLockerTest2(){
        assertEquals("problem with creating locker",-1,testObject.createLocker(1000,100));

    }

    /**
     * Testing crating locker with illegal capacity.
     */
    @Test
    public void createLockerTest3(){
        assertEquals("problem with creating locker",-2,testObject.createLocker(4,-100));
    }

    /**
     * Testing crating locker when we reached the max number of lockers.
     */
    @Test
    public void createLockerTest4(){
        testObject.createLocker(2,30);
        testObject.createLocker(3,10);
        testObject.createLocker(6,10);
        testObject.createLocker(1,10);
        testObject.createLocker(1,10);
        assertEquals("problem with creating locker",-3,testObject.createLocker(4,100));
    }

    /**
     * Testing the get long term storage method.
     */
    @Test
    public void getLongTermStorageTest(){
        assertTrue("problem with getting long term storage",
                testObject.getLongTermStorage() instanceof LongTermStorage);
    }


    @Test
    /**
     * Testing get crew ids method.
     */
    public void getCrewIDs() {
        int[] testIdCrewList = new int[]{1, 2, 3, 4, 5, 6};
        assertArrayEquals("problem with getCrew id", testIdCrewList, testObject.getCrewIDs());
        int[] newTestCrewIdList = new int[]{1,7, 5, 6};
        testObject = new Spaceship("testSpaceship",newTestCrewIdList,5);
        assertFalse(Arrays.equals(testIdCrewList,testObject.getCrewIDs()));

    }


    /**
     * Testing get lockers method.
     */
    @Test
    public void getLockersTest(){
        testObject.createLocker(2,30);
        testObject.createLocker(3,1);
        testObject.createLocker(1,3);
        testObject.createLocker(4,3);
        Locker[] lockers = testObject.getLockers();
        int lockerCountTest = 0;
        for(int j=0;j<lockers.length; j++) {
            if (lockers[j] instanceof Locker) {
                lockerCountTest++;
            }
        }
        assertEquals("problem with getting lockers list",4,lockerCountTest);
        assertEquals("problem with getting lockers list",5,testObject.getLockers().length);
    }
}











