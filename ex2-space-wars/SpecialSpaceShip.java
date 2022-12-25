import oop.ex2.GameGUI;

import java.awt.*;


/**
 * This class represent the special type spaceship. it inherits all the methods and data members from
 * the abstract class SpaceShips, and overrides few of it's functions- doAction,getImage and fire .
 * This space ship is has special skills- its following the closest space ship and shots it.
 * Its special skill is that its energy doesnt gets down after firing and it can fire without waiting for 7 rounds.
 * more over- the fire method fires 2 shots every time.
 */
public class SpecialSpaceShip extends SpaceShip {
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
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleOfClosestShip = getPhysics().angleTo(closestShip.getPhysics());
        int side = turnTo(angleOfClosestShip);
        getPhysics().move(true,side);
        fire(game);
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
    Image getImage() {
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     *  fire a shot.
     * @param game the game object.
     */
    @Override
    public void fire(SpaceWars game) {
        game.addShot(this.spaceShipPhys);
        game.addShot(this.spaceShipPhys);

    }
}
