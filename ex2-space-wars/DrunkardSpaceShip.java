import oop.ex2.GameGUI;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.util.Random;
/**
 * This class represent the Drunk type spaceship. it inherits all the methods and data members from
 * the abstract class SpaceShips, and overrides one of it's functions- doAction.
 * This space ship driver is drunk and there for he moves only in one direction (right) and fires at random location.
 */

public class DrunkardSpaceShip extends SpaceShip{
    final int RANGE = 2;

    /**
     * This method is using Random class in order to pick random number between -2 t0 2.
     * @return
     */
    public int rand(){
        Random r = new Random();
        int low = -RANGE;
        int high = RANGE;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    void doAction(SpaceWars game) {
        getPhysics().move(false,1);
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (getPhysics().distanceFrom(closestShip.getPhysics()) < Math.random()){
            fire(game);
        }
        chargeCurrentEnergy();
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     *      * ship with or without the shield. This will be displayed on the GUI at
     *      * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}
