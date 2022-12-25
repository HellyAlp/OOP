import oop.ex2.GameGUI;

import java.awt.*;
import java.lang.reflect.GenericArrayType;

/**
 * This class represent the Basher type spaceship. it inherits all the methods and data members from the abstract class
 * SpaceShips, and overrides one of it's functions- doAction.
 * This space ship attempts to collide with other ship.
 */
public class BasherSpaceShip extends SpaceShip {
    private final double MIN_DISTANCE = 0.19;
    private final int ADD_COUNT = 1;


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    void doAction(SpaceWars game) {
        setRoundsCounter(getRoundsCounter() + ADD_COUNT);
        setShieldStatus(false);
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleOfClosestShip = getPhysics().angleTo(closestShip.getPhysics());
        getPhysics().move(true,turnTo(angleOfClosestShip));
        if (getPhysics().distanceFrom(closestShip.getPhysics())<= MIN_DISTANCE ){
            shieldOn();
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
        if (getShieldStatus()){
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}
