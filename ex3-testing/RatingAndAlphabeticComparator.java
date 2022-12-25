import oop.ex3.searchengine.*;
import java.util.*;

/**
 *  This class creates the RatingAndAlphabeticComparator.
 */
public class RatingAndAlphabeticComparator implements Comparator<Hotel>{
    public int compare(Hotel hotel1, Hotel hotel2) {
        int compering = hotel2.getStarRating() - hotel1.getStarRating();
        if (compering == 0){
            compering = hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
        }
        return compering;
    }
}
