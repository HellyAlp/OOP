import oop.ex3.spaceship.*;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/***
 * This Class contains test methods testing the long term storage class and its methods.
 */
public class LongTermTest {

    LongTermStorage testObject;
    private Item baseballBat = ItemFactory.createSingleItem("baseball bat");
    private Item helmet1 = ItemFactory.createSingleItem("helmet, size 1");
    private Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");
    private Item sporesEngine = ItemFactory.createSingleItem("spores engine");
    private Item football = ItemFactory.createSingleItem("football");


    /***
     * creating new LTS object before every test.
     */
    @Before
    public void creatingObject(){
        testObject = new LongTermStorage();
    }


    /**
     * Testing the add item method- testing adding items .
     */
    @Test
    public void addItemTest1(){
        assertEquals("problem with adding item",0,testObject.addItem(baseballBat,200));
    }
    /**
     * Testing the add item method- testing adding items if n (number of items) is 0.
     */
    @Test
    public void addItemTest2(){
        assertEquals("problem with adding item",-1,testObject.addItem(helmet1,0));
    }
    /**
     * Testing the add item method- testing adding items if n (number of items) is negative .
     */
    @Test
    public void addItemTest3(){
        assertEquals("problem with adding item",-1,testObject.addItem(sporesEngine,-200));
    }
    /**
     * Testing the add item method- testing adding items if n (number of items) is too big.
     */
    @Test
    public void addItemTest4(){
        assertEquals("problem with adding item",-1,testObject.addItem(helmet3,20000));
    }

    /**
     * Testing reset inventory method.
     */
    @Test
    public void resetInventoryTest(){
        testObject.addItem(baseballBat,100);
        testObject.addItem(helmet1,100);
        Map<String,Integer> testInventory = new HashMap<>();
        testObject.resetInventory();
        assertEquals("problem with reset inventory", testInventory.keySet(),testObject.getInventory().keySet());
    }





    @Test
    public void getItemCountTest(){
        testObject.addItem(baseballBat,100);
        assertEquals("problem with getting item count",100,testObject.
                getItemCount("baseball bat"));
        assertEquals("problem with getting item count",0,testObject.getItemCount("football"));
    }

    /**
     * Testing the getInventory method.By checking the keys and values.
     */
    @Test
    public void getInventoryTest(){
        testObject.addItem(baseballBat,100);
        Map<String,Integer> testInventory = new HashMap<>();
        testInventory.put("baseball bat",100);
        assertEquals("problem with inventory key", testInventory.keySet(),testObject.getInventory().keySet());
        assertEquals("problem with inventory value", testInventory.get("baseball bat"),
                testObject.getInventory().get(baseballBat.getType()));
    }

    /**
     * Testing getCapacity method.
     */
    @Test
    public void getCapacityTest(){
        assertEquals("problem with getting locker capacity",1000,testObject.getCapacity());
    }

    /**
     * Testing getAvailableCapacity method by adding and removing items and checking tha available place.
     */
    @Test
    public void getAvailableCapacityTest(){
        assertEquals("problem with getting locker available capacity",1000,testObject.getAvailableCapacity());
        testObject.addItem(sporesEngine,30);
        assertEquals("problem with getting locker available capacity",700,testObject.getAvailableCapacity());
        testObject.addItem(sporesEngine,10);
        assertEquals("problem with getting locker available capacity",600,testObject.getAvailableCapacity());
        testObject.addItem(helmet3,20);
        assertEquals("problem with getting locker available capacity",500,testObject.getAvailableCapacity());
        testObject.resetInventory();
        assertEquals("problem with getting locker available capacity",1000,testObject.getAvailableCapacity());





    }


















}
