import oop.ex2.*;

/**
 * This class has a single method and it is used by the supplied driver to create all the spaceships objects according
 * to the command line arguments.
 */
public class SpaceShipFactory {
    private final static String AGGRESIVE = "a";
    private final static String RUNNER = "r";
    private final static String HUMAN = "h";
    private final static String BASHER = "b";
    private final static String DRUNKARD = "d";
    private final static String SPECIAL = "s";

    /***
     * This method is receiving a arrays contains strings, and return an array that contains the SpaceShip objects
     * according to the types from the args.
     * @param args array of string representing ths desired spaceships types.
     * @return An array of Space ships objects.
     */

    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] spaceShipsList = new SpaceShip[args.length];

        for(int i = 0; i < args.length; i++){
            if(args[i].equals(RUNNER)){
                RunnerSpaceShip newSpaceShip = new RunnerSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;
            }
            else if(args[i].equals(BASHER)){
                BasherSpaceShip newSpaceShip = new BasherSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;
            }
            else if(args[i].equals(AGGRESIVE)){
                AggressiveSpaceShip newSpaceShip = new AggressiveSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;
            }
            else if(args[i].equals(HUMAN)){
                HumanSpaceShip newSpaceShip = new HumanSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;
            }
            else if(args[i].equals(DRUNKARD)){
                DrunkardSpaceShip newSpaceShip = new DrunkardSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;

            }
            else if(args[i].equals(SPECIAL)){
                SpecialSpaceShip newSpaceShip = new SpecialSpaceShip();
                spaceShipsList[i] = newSpaceShip;
                continue;
            }
        }
        return spaceShipsList;



    }
}
