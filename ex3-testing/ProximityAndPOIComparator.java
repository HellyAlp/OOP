import oop.ex3.searchengine.*;
import java.util.*;


/**
 * This class creates the ProximityAndPOIComparator.
 */
public class ProximityAndPOIComparator implements Comparator<Hotel>{
    private double latitude;
    private double longitude;
    private int ZERO =0;
    private int ONE = 1;
    public ProximityAndPOIComparator(double latitudePoint,double longitudePoint){
        latitude = latitudePoint;
        longitude = longitudePoint;
    }
    public int compare(Hotel hotel1, Hotel hotel2) {
        double latitude1 = hotel1.getLatitude();
        double longitude1 = hotel1.getLongitude();
        double latitude2 = hotel2.getLatitude();
        double longitude2 = hotel2.getLongitude();
        double distanceFromHotel1 = Math.hypot(latitude1-latitude,longitude1-longitude);
        double distanceFromHotel2 = Math.hypot(latitude2-latitude,longitude2-longitude);
        if(distanceFromHotel2 > distanceFromHotel1){
            return -ONE;
        }
        else if (distanceFromHotel2 == distanceFromHotel1){
            if(hotel1.getNumPOI() > hotel2.getNumPOI()){
                return -ONE;
            }
            else if(hotel1.getNumPOI() == hotel2.getNumPOI()){
                return ZERO;
            }
            else{
                return ONE;
            }
        }
        else{
            return ONE;
        }

    }
}