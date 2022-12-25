import oop.ex2.GameGUI;

import java.awt.*;

/**
 * This class represent the Human controlled type spaceship. it inherits all the methods and data members from
 * the abstract class SpaceShips, and overrides one of it's functions- doAction.
 * This space ship is controlled by the user. The keys are: left-arrow and right-arrow to
 * turn, up-arrow to accelerate, ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport.
 * The order of precedence is: teleport,accelerate and turn, shield activated, firing a shot, regenerating energy.
 */
public class HumanSpaceShip extends SpaceShip {
    private final int NO_TURN = 0;
    private final int TURN_RIGHT = 1;
    private int turnTo;
    private boolean upPressed;
    private boolean leftPress;
    private boolean rightPress;


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    void doAction(SpaceWars game) {
        setRoundsCounter(getRoundsCounter()+ TURN_RIGHT);
        if (game.getGUI().isTeleportPressed()){
            teleport();
        }
        upPressed = game.getGUI().isUpPressed();
        leftPress = game.getGUI().isLeftPressed();
        rightPress = game.getGUI().isRightPressed();
        if (leftPress ){turnTo = TURN_RIGHT;}
        if (rightPress ){turnTo = -TURN_RIGHT;}
        if ((leftPress && rightPress)||(!leftPress &&!rightPress)){turnTo = NO_TURN;}
        getPhysics().move(upPressed,turnTo);
        if (game.getGUI().isShieldsPressed()){
            shieldOn();
        }
        else{
            setShieldStatus(false);
        }
        if (game.getGUI().isShotPressed()){
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
        if (getShieldStatus()){
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }
}
