import oop.ex2.*;
import java.awt.*;
import java.math.*;
/**
 * This class represent the Runner type spaceship. it inherits all the methods and data members from
 * the abstract class SpaceShips, and overrides one of it's functions- doAction.
 * This space ship attempts to run away from the fight.
 */
public class RunnerSpaceShip extends SpaceShip {
    final double MIN_DISTANCE = 0.25;
    final double MIN_ANGLE = 0.23;
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
        getPhysics().move(true,-turnTo(angleOfClosestShip));
        if (getPhysics().distanceFrom(closestShip.getPhysics())< MIN_DISTANCE &&
                Math.abs(angleOfClosestShip) <MIN_ANGLE){
            teleport();

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
