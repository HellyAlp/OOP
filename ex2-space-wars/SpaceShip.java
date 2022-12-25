import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * This is the abstract class SpaceShip- class contains all the data members and actions
 * (different methods like fire,shieldUp,gotHit etc.) that every spaceship needs to know and do.
 * every spaceship type inherits all of this class methods and data members.
 */
abstract class SpaceShip{
    private final int MAX_HEALTH = 22;
    private final int MAX_ENERGY = 210;
    private final int CURRENT_ENERGY = 190;
    private final int FIRE_ENERGY_DOWN = 19;
    private final int BASHING_ENERGY_UP = 18;
    private final int COLLUSION_ENERGY_DOWN = 10;
    private final int SHIELD_ENERGY_DOWN =3;
    private final int WAIT_AFTER_FIRE  = 7;
    private final int TELEPORT_ENERGY_DOWN = 140;
    private final int NO_ROUNDS = 0;
    private final int TURN_RIGHT = 1;
    private boolean shieldStatus;
    private int maxEnergyLevel;
    private int currentEnergyLevel;
    private int healthLevel;
    private int roundsCounter;
    private int shotsFiredat;
    SpaceShipPhysics spaceShipPhys;


    public SpaceShip(){
        spaceShipPhys = new SpaceShipPhysics();
        shieldStatus = false;
        maxEnergyLevel = MAX_ENERGY;
        currentEnergyLevel = CURRENT_ENERGY;
        healthLevel = MAX_HEALTH;
        roundsCounter = NO_ROUNDS;
        shotsFiredat = NO_ROUNDS;

    }



    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if(getShieldStatus()){
            setMaxEnergyLevel(getMaxEnergyLevel() + BASHING_ENERGY_UP);
            setCurrentEnergyLevel(getCurrentEnergyLevel() + BASHING_ENERGY_UP);
        }
        else{
            setHealthLevel(getHealthLevel() - TURN_RIGHT);
            setMaxEnergyLevel(getMaxEnergyLevel() - COLLUSION_ENERGY_DOWN);
            if (getCurrentEnergyLevel() > getMaxEnergyLevel()){
                setCurrentEnergyLevel(getMaxEnergyLevel());
            }
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        spaceShipPhys = new SpaceShipPhysics();
        shieldStatus = false;
        maxEnergyLevel = MAX_ENERGY;
        currentEnergyLevel = CURRENT_ENERGY;
        healthLevel = MAX_HEALTH;
        roundsCounter = NO_ROUNDS;
        shotsFiredat = NO_ROUNDS;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return healthLevel == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return spaceShipPhys;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(!getShieldStatus()){
            setHealthLevel(getHealthLevel() - TURN_RIGHT);
            setMaxEnergyLevel(getMaxEnergyLevel() - COLLUSION_ENERGY_DOWN);
            if (getCurrentEnergyLevel() > getMaxEnergyLevel()) {
                setCurrentEnergyLevel(getMaxEnergyLevel());
            }
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     *      * ship with or without the shield. This will be displayed on the GUI at
     *      * the end of the round.
     * 
     * @return the image of this ship.
     */
    abstract Image getImage();
    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (getCurrentEnergyLevel() - FIRE_ENERGY_DOWN >=0){
            if (getShotsFiredat() == NO_ROUNDS || getRoundsCounter()-getShotsFiredat() > WAIT_AFTER_FIRE){
                game.addShot(this.spaceShipPhys);
                 setShotsFiredat(getRoundsCounter());
                setCurrentEnergyLevel(getCurrentEnergyLevel() - FIRE_ENERGY_DOWN);
            }
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(getCurrentEnergyLevel()- SHIELD_ENERGY_DOWN >=0 ){
            setShieldStatus(true);
            setCurrentEnergyLevel(getCurrentEnergyLevel()- SHIELD_ENERGY_DOWN);
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (getCurrentEnergyLevel() - TELEPORT_ENERGY_DOWN >=0){
            setCurrentEnergyLevel(getCurrentEnergyLevel() - TELEPORT_ENERGY_DOWN);
            this.spaceShipPhys = new SpaceShipPhysics();
        }
    }


    /**
     * A method that gets an angle(double) and return which way the space ship needs to turn.
     * 1 for right (if the ange is above 0), -1 for left (if the angle is under 0) and 0 for staying in ths same way
     * if the angle is 0.
     * @param angle double
     * @return int 1, -1 or 0
     */
    public int turnTo(double angle){
        if (angle > NO_ROUNDS){
            return TURN_RIGHT;
        }
        else if (angle < NO_ROUNDS){
            return -TURN_RIGHT;
        }
        return NO_ROUNDS;
    }

    /**
     * A method that adds one energy unit to the current energy of the spaceship every turn.
     */
    public void chargeCurrentEnergy(){
        if (getCurrentEnergyLevel() + TURN_RIGHT <= maxEnergyLevel) {
            setCurrentEnergyLevel(getCurrentEnergyLevel() + TURN_RIGHT);
        }
    }

    /**
     * set the shield status
     * @param val boolean true/false (on/off)
     */
    public void setShieldStatus(boolean val){
        shieldStatus = val;
    }

    /**
     * gets the shield status true/false (on or off)
     * @return
     */
    public boolean getShieldStatus(){
        return shieldStatus;
    }

    /**
     * gets the current level of energy.
     * @return
     */
    public int getCurrentEnergyLevel() { return currentEnergyLevel; }

    /**
     * sests the current level of energy.
     */
    public void setCurrentEnergyLevel(int val){ this.currentEnergyLevel = val; }

    /**
     * sets the last rount that shot was fire at.
     * @param shotsFiredat
     */
    public void setShotsFiredat(int shotsFiredat) {
        this.shotsFiredat = shotsFiredat;
    }

    /**
     * gets the round that the last shot was fired at.
     */
    public int getShotsFiredat() {
        return shotsFiredat;
    }

    /**
     * sets the number of rounds.
     */
    public void setRoundsCounter(int roundsCounter) {
        this.roundsCounter = roundsCounter;
    }

    /**
     * gets the number of rounds
     * @return
     */
    public int getRoundsCounter() {
        return roundsCounter;
    }

    /**
     * sets the health level
     */
    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    /**
     * gets the health level
     * @return health level
     */
    public int getHealthLevel() {
        return healthLevel;
    }

    /**
     * sets the max energy level
     * @param maxEnergyLevel
     */
    public void setMaxEnergyLevel(int maxEnergyLevel) {
        this.maxEnergyLevel = maxEnergyLevel;
    }

    /**
     * gets the max energy level
     * @return  the max energy level
     */
    public int getMaxEnergyLevel() {
        return maxEnergyLevel;
    }

}
