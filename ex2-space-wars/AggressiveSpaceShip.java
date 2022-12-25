import oop.ex2.GameGUI;

import java.awt.*;

/**
 * This class represent the Aggressive type spaceship. it inherits all the methods and data members from
 * the abstract class SpaceShips, and overrides one of it's functions- doAction.
 * This space ship pursues other ships and tries to fire at them.
 */

public class AggressiveSpaceShip extends SpaceShip {
    private final double MIN_ANGLE = 0.21;
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
        int side = turnTo(angleOfClosestShip);
        getPhysics().move(true,side);
        if (Math.abs(angleOfClosestShip)< MIN_ANGLE ){
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
