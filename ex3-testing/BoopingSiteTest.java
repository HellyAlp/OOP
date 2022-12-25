import oop.ex3.searchengine.*;
import org.junit.*;
import static org.junit.Assert.*;


/***
 * This Class contains test methods testing the BoopingSite class and its methods.
 */
public class BoopingSiteTest {
    private static String nameTest;
    private static Hotel[] emptyArray = {};
    private static BoopingSite bigDataSetSite;
    private static BoopingSite smallDataSetSite;
    private static BoopingSite emptyDataSetSite;
    private static double latCoord;
    private static double longCoord;



    @BeforeClass
    public static void createTestObjects(){
        bigDataSetSite= new BoopingSite("hotels_dataset.txt");
        smallDataSetSite= new BoopingSite("hotels_dataset.txt");
        emptyDataSetSite = new BoopingSite("hotels_tst2.txt");
        latCoord = 23.00001;
        longCoord = 67.90231;
    }


     //Testes for getHotelsInCityByRating method.


    /**
     * Testing the getHotelsInCityByRating method  for a city that has no hotels in the given dataset.
     * The method should return an empty array.
     */
    @Test
    public void noHotelsInCityTest(){
        assertArrayEquals("error: problem with output with getHotelsInCityByRating when no hotels in city"
                ,emptyArray,smallDataSetSite.getHotelsInCityByRating("Dharamshala"));
    }

    /**
     * Testing the getHotelsInCityByRating method  for a dataset that has no hotels.
     * The method should return an empty array.
     */
    @Test
    public void noHotelsInDataSetRating(){
        assertArrayEquals("error: problem with output with getHotelsInCityByRating when dataset is empty"
                ,emptyArray,emptyDataSetSite.getHotelsInCityByRating("Dharamshala"));
    }

    /**
     * Testing the getHotelsInCityByRating method when the input of city is empty.
     * The method should return an empty array.
     */
    @Test
    public void HotelsInCityEmptyInputTest(){
        assertArrayEquals("error: problem with output with getHotelsInCityByRating when city is an empty string"
                ,emptyArray,bigDataSetSite.getHotelsInCityByRating(""));
    }


    /**
     * Testing the getHotelsInCityByRating method by checking if all the hotels in the return array are in the
     * city we wanted.
     */
    @Test
    public void getHotelsInCityTest(){
        String city1 = "Rishikesh";
        Hotel[] testHotelArray = bigDataSetSite.getHotelsInCityByRating(city1);
        int counter = 0;
        for (int i=0; i< testHotelArray.length;i++){
            if(testHotelArray[i].getCity().equals(city1)){
                counter+=1;
            }
        }
        assertEquals("error: problem with output with getHotelsInCityByRating- containing hotels from different " +
                "cities",counter,testHotelArray.length);
    }
    /**
     * Testing if getHotelsInCityByRating method sort the hotels in the city by rating.
     */
    @Test
    public void getHotelInCityRatingTest() {
        String city2 = "Dharamshala";
        Hotel[] testHotelArray = bigDataSetSite.getHotelsInCityByRating(city2);
        int counter = 0;
        for (int i = 0; i < testHotelArray.length - 1; i++) {
            if (testHotelArray[i].getStarRating() >= testHotelArray[i + 1].getStarRating()) {
                counter += 1;
            }
            else{
                break;
            }
        }
        assertEquals("error: problem with output with getHotelsInCityByRating- the array is not sorted by " +
                "rating",counter,testHotelArray.length);
    }
    /**
     * Testing if getHotelsInCityByRating method sort the hotels with the same rate is sorted in alphabetic order
     */
    @Test
    public void getHotelInCityAlphabeticalTest() {
        String city3 = "Manali";
        Hotel[] testHotelArray = bigDataSetSite.getHotelsInCityByRating(city3);
        boolean check = true;
        for (int i = 0; i < testHotelArray.length - 1; i++) {
            if (testHotelArray[i].getStarRating() == testHotelArray[i + 1].getStarRating()){
                if (testHotelArray[i].getPropertyName().compareTo(testHotelArray[i + 1].getPropertyName()) > 0){
                    check = false;
                    break;
                }
            }
        }
        assertTrue("Error: the hotels with same rating not sorted in alphabetic order", check);
    }


     //Tests for getHotelsByProximity method.


    /**
     * Testing the getHotelsByProximity method  for a dataset that has no hotels.
     * The method should return an empty array.
     */
    @Test
    public void noHotelsInDataSetProximityTest() {
        assertArrayEquals("error: problem with output with getHotelsByProximity when dataset is empty"
                ,emptyArray,emptyDataSetSite.getHotelsByProximity(latCoord,longCoord));
    }

    /**
     * Testing the getHotelsByProximity method with invalid latitude.
     * The method should return an empty array.
     */
    @Test
    public void invalidLatitudeProximityTest() {
        assertArrayEquals("error: problem with output with getHotelsByProximity when latitude is invalid"
                ,emptyArray,smallDataSetSite.getHotelsByProximity(-100,longCoord));
    }

    /**
     * Testing the getHotelsByProximity method with invalid longitude.
     * The method should return an empty array.
     */
    @Test
    public void invalidLongitudeProximityTest() {
        assertArrayEquals("error: problem with output with getHotelsByProximity when longitude is invalid"
                ,emptyArray,smallDataSetSite.getHotelsByProximity(latCoord,200));
    }

    /**
     * Testing if getHotelsByProximity method sort the hotels with the same distance is in POI order.
     */
    @Test
    public void getHotelProximityPOITest(){
        Hotel[] testHotelArray = bigDataSetSite.getHotelsByProximity(latCoord,longCoord);
        boolean check = true;
        for(int i = 0; i < testHotelArray.length-1; i++){
            double dis1 = Math.hypot(testHotelArray[i].getLatitude() - latCoord,
                    testHotelArray[i].getLongitude() - longCoord);
            double dis2 = Math.hypot(testHotelArray[i + 1].getLatitude() - latCoord,
                    testHotelArray[i + 1].getLongitude() - longCoord);
            if (dis1==dis2 && testHotelArray[i].getNumPOI()< testHotelArray[i+1].getNumPOI()){
                check = false;
                break;
            }
        }
        assertTrue("Error: the hotels with same distance not sorted in POI order", check);
    }

    /**
     * Testing if getHotelsByProximity method sort the hotels by distance.
     */
    @Test
    public void getHotelProximityTest(){

        Hotel[] testHotelArray = bigDataSetSite.getHotelsByProximity(latCoord,longCoord);
        boolean check = true;
        for(int i = 0; i < testHotelArray.length-1; i++){
            double dis1 = Math.hypot(testHotelArray[i].getLatitude() - latCoord,
                    testHotelArray[i].getLongitude() - longCoord);
            double dis2 = Math.hypot(testHotelArray[i + 1].getLatitude() - latCoord,
                    testHotelArray[i + 1].getLongitude() - longCoord);
            if (dis2<dis1){
                check = false;
                break;
            }
        }
        assertTrue("Error: the hotels is not sorted by distance", check);

    }

    //    public Hotel[] getHotelsInCityByProximity(String city,double latitude, double longitude){

    //Tests for getHotelsInCityByProximity method.

    /**
     * Testing the getHotelsInCityByProximity method  for a city that has no hotels in the given dataset.
     * The method should return an empty array.
     */
    @Test
    public void noHotelsInCityProximityTest(){
        assertArrayEquals("error: problem with output with getHotelsInCityByProximity when no hotels in city"
                ,emptyArray,smallDataSetSite.getHotelsInCityByRating("Dharamshala"));
    }


    /**
     * Testing the getHotelsInCityByProximity method  for a dataset that has no hotels.
     * The method should return an empty array.
     */
    @Test
    public void noHotelsInDataSetInCityProximityTest() {
        assertArrayEquals("error: problem with output with getHotelsInCityByProximity when dataset is empty"
                ,emptyArray,emptyDataSetSite.getHotelsByProximity(latCoord,longCoord));
    }

    /**
     * Testing the getHotelsInCityByProximity method with invalid latitude.
     * The method should return an empty array.
     */
    @Test
    public void invalidLatitudeProximityInCityTest() {
        assertArrayEquals("error: problem with output with getHotelsInCityByProximity when latitude is invalid"
                ,emptyArray,smallDataSetSite.getHotelsByProximity(-100,longCoord));
    }

    /**
     * Testing the getHotelsInCityByProximity method with invalid longitude.
     * The method should return an empty array.
     */
    @Test
    public void invalidLongitudeProximityInCityTest() {
        assertArrayEquals("error: problem with output with getHotelsInCityByProximity when longitude is invalid"
                ,emptyArray,smallDataSetSite.getHotelsByProximity(latCoord,200));
    }



    /**
     * Testing if getHotelsInCityByProximity method sort the hotels with the same distance is in POI order.
     */
    @Test
    public void getHotelProximityInCityPOITest(){
        String city3 = "Manali";
        Hotel[] testHotelArray = bigDataSetSite.getHotelsInCityByProximity(city3,latCoord,longCoord);
        boolean check = true;
        for(int i = 0; i < testHotelArray.length-1; i++){
            double dis1 = Math.hypot(testHotelArray[i].getLatitude() - latCoord,
                    testHotelArray[i].getLongitude() - longCoord);
            double dis2 = Math.hypot(testHotelArray[i + 1].getLatitude() - latCoord,
                    testHotelArray[i + 1].getLongitude() - longCoord);
            if (dis1==dis2 && testHotelArray[i].getNumPOI()< testHotelArray[i+1].getNumPOI()){
                check = false;
                break;
            }
        }
        assertTrue("Error: the hotels in city with same distance not sorted in POI order", check);

    }

    /**
     * Testing if getHotelsInCityByProximity method sort the hotels by distance.
     */
    @Test
    public void getHotelInCityProximityTest(){
        String city3 = "Manali";
        Hotel[] testHotelArray = bigDataSetSite.getHotelsInCityByProximity(city3,latCoord,longCoord);
        boolean check = true;
        for(int i = 0; i < testHotelArray.length-1; i++){
            double dis1 = Math.hypot(testHotelArray[i].getLatitude() - latCoord,
                    testHotelArray[i].getLongitude() - longCoord);
            double dis2 = Math.hypot(testHotelArray[i + 1].getLatitude() - latCoord,
                    testHotelArray[i + 1].getLongitude() - longCoord);
            if (dis2<dis1){
                check = false;
                break;
            }
        }
        assertTrue("Error: the hotels in city is not sorted by distance", check);

    }



































}
