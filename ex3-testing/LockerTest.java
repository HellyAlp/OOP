import oop.ex3.spaceship.*;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/***
 * This Class contains test methods testing the Locker class and its methods.
 */
public class LockerTest {
    private Locker testObject;
    private Item[][] constraintsList;
    private LongTermStorage lts;
    private int capacity;
    private Item baseballBat = ItemFactory.createSingleItem("baseball bat");
    private Item helmet1 = ItemFactory.createSingleItem("helmet, size 1");
    private Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");
    private Item sporesEngine = ItemFactory.createSingleItem("spores engine");
    private Item football = ItemFactory.createSingleItem("football");

    /***
     * creating new Locker object before every test.
     */
    @Before
    public void creatObject(){
        lts = new LongTermStorage();
        capacity = 500;
        constraintsList= ItemFactory.getConstraintPairs();
        testObject = new Locker(lts,capacity,constraintsList);
    }


    /**
     * Testing the add item method- testing adding two items that is part of the constraint pairs.
     */
    @Test
    public void addItemTest1(){
        assertEquals("problem with adding item",0,testObject.addItem(baseballBat,2));
        assertEquals("problem with adding item",-2,testObject.addItem(football,3));
        testObject.removeItem(baseballBat,2);
        assertEquals("problem with adding item",0,testObject.addItem(football,3));
        assertEquals("problem with adding item",-2,testObject.addItem(baseballBat,2));
    }


    /**
     * Testing the add item method- testing adding items if n (number of items) is 0, negative or to big,
     */
    @Test
    public void addItemTest2(){
        assertEquals("problem with adding item",-1,testObject.addItem(helmet1,0));
        assertEquals("problem with adding item",0,testObject.addItem(baseballBat,20));
        assertEquals("problem with adding item",-1,testObject.addItem(sporesEngine,-200));
        assertEquals("problem with adding item",-1,testObject.addItem(helmet3,2000));

    }

    /**
     * Testing the add item method- testing adding items and moving items to Long term storage.
     */
    @Test
    public void addItem3(){
        assertEquals("problem with adding item",0,testObject.addItem(sporesEngine,10));
        assertEquals("problem with adding item",1,testObject.addItem(sporesEngine,20));


    }

    /**
     * a test that tests what happens if the number of items given is 0, negative or more than the locker capacity.
     */
    @Test
    public void removeItemTest1(){
        testObject.addItem(helmet3,3);
        testObject.addItem(sporesEngine,50);
        assertEquals("problem with removing item",-1,testObject.removeItem(helmet3,-50));
        assertEquals("problem with removing item",-1,testObject.removeItem(sporesEngine,0));
        assertEquals("problem with removing item",-1,testObject.removeItem(sporesEngine,100));
    }

    /**
     * A test that tests removing more item than there is in the locker.
     */
    @Test
    public void removeItemTest2(){
        testObject.addItem(baseballBat,100);
        assertEquals("problem with removing item",0,testObject.removeItem(baseballBat,50));
        assertEquals("problem with removing item",-1,testObject.removeItem(baseballBat,51));
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
        assertEquals("problem with getting locker capacity",500,testObject.getCapacity());
        testObject = new Locker(lts,1000,constraintsList);
        assertEquals("problem with getting locker capacity",1000,testObject.getCapacity());
    }

    /**
     * Testing getAvailableCapacity method by adding and removing items and checking tha available place.
     */
    @Test
    public void getAvailableCapacityTest(){
        testObject.addItem(helmet1,30);
        assertEquals("problem with getting locker available capacity",410,testObject.getAvailableCapacity());
        testObject.removeItem(helmet1,20);
        assertEquals("problem with getting locker available capacity",470,testObject.getAvailableCapacity());
        testObject.addItem(sporesEngine,20);
        assertEquals("problem with getting locker available capacity",270,testObject.getAvailableCapacity());

    }










}
