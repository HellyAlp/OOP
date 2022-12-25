import oop.ex3.searchengine.HotelDataset;
import oop.ex3.searchengine.Hotel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * A class representing hotel booking site with 3 search methodologies.
 */
public class BoopingSite {
    private int ZERO = 0;
    private int LAT_RANGE = 90;
    private int LONG_RANGE =180;

    private String hotelDataset;
    private Hotel[] emptyArray;
    private Hotel[] hotelsArray;

    /**
     * This constructor receives as parameter a string, which is the name of the dataset.
     * @param name the name of the dataset.
     */
    public BoopingSite(String name){
        hotelDataset = name;
        emptyArray = new Hotel[]{};
        hotelsArray = HotelDataset.getHotels(hotelDataset);

    }

    /**
     *This method returns an array of hotels located in the given city, sorted from the highest star-rating to the lowest. Hotels that have the
     * same rating will be organized according to the alphabet order of the hotel’s (property) name. In case
     * there are no hotels in the given city, this method returns an empty array.
     * @param city The city we want to get the hotels in.
     * @return Array- empty or with hotel objects.
     */
    public Hotel[] getHotelsInCityByRating(String city){
        RatingAndAlphabeticComparator comparator = new RatingAndAlphabeticComparator();
        ArrayList<Hotel> cityHotelsList = new ArrayList<Hotel>(){};
        for (Hotel hotel : hotelsArray) {
            if (hotel.getCity().equals(city)) {
                cityHotelsList.add(hotel);
            }
        }
        if(cityHotelsList.size() == ZERO) {
            return emptyArray;
        }
        else{
            Hotel[] cityHotelsArray = new Hotel[cityHotelsList.size()];
            cityHotelsArray = cityHotelsList.toArray(cityHotelsArray);
            Arrays.sort(cityHotelsArray,comparator);

            return cityHotelsArray;
        }

    }

    /**
     *This method returns an array of hotels, sorted according to their euclidean distance from the given geographic
     * location, in ascending order. Hotels that are at the same distance from the given location are organized
     * according to the number of points-of-interest for which they are close to (in a decreasing order). In
     * case of illegal input, this method returns an empty array
     * @param latitude a point in x axis line
     * @param longitude a point in y axis line
     * @return Array- empty or with hotel objects.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude){
        return proximityHelper( latitude, longitude,hotelsArray);
    }

    /***
     * function that is a helper for sorting arrays in proximity order.
     */

    private Hotel[] proximityHelper(double latitude, double longitude,Hotel[] hotelsArray){
        ProximityAndPOIComparator comparator = new ProximityAndPOIComparator(latitude,longitude);
        //ArrayList<Hotel> hotelList = new ArrayList<Hotel>(){};
        if((checkLatitude(latitude))||checkLongitude(longitude) || hotelsArray.length == ZERO){
            return emptyArray;
        }
        //for (Hotel hotel : hotelsArray) {
        //    hotelList.add(hotel);
        //}
        Arrays.sort(hotelsArray,comparator);
        return hotelsArray;
    }


    /**
     * checking if latitude number is in range.
     */
    private boolean checkLatitude(double latitude){
        return latitude < -LAT_RANGE || latitude > LAT_RANGE;
    }

    /**
     * checking if longitude number is in range.
     */

    private boolean checkLongitude(double longitude){
        return longitude < -LONG_RANGE || longitude > LONG_RANGE;
    }




    /**
     * This method returns an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order. Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     * @param city  The city we want to get the hotels in.
     * @param latitude a point in x axis line
     * @param longitude a point in y axis line
     * @return Array- empty or with hotel objects.
     */
    public Hotel[] getHotelsInCityByProximity(String city,double latitude, double longitude){
        ArrayList<Hotel> cityHotelsList = new ArrayList<Hotel>(){};
        for (Hotel hotel : this.hotelsArray) {
            String nameP = hotel.getCity();
            if (nameP.equals(city)) {
                cityHotelsList.add(hotel);
            }
        }
        Hotel[] cityHotelsArray = new Hotel[cityHotelsList.size()];
        for(int i=0; i<cityHotelsList.size(); i++){
            cityHotelsArray[i] = cityHotelsList.get(i);
        }
        return proximityHelper(latitude,longitude,cityHotelsArray);
    }




}
