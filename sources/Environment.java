
//The Environment Class
public class Environment {

    //Define the environment directions
    private static enum directions {
        NORTH,
        SOUTH,
        EAST,
        WEST
    };

    //Define the signal colors
    private static enum signalColors {
        RED,
        GREEN
    };

    //Bump flag
    private boolean bump = false;

    //Map dimensions
    private final int MAP_X;
    private final int MAP_Y;

    //Contains the map matrix
    private final int[][] map;

    //The accident`s site [x,y]
    private int[] accidentSite = new int[2];

    //The car`s position [x,y]
    private int[] carPosition = new int[2];
    private directions carFacing;

    //Contains the sinalColors for the map [NS, WE] 
    private signalColors[] signal = new signalColors[2];

    //Constructor method
    public Environment(int x, int y, int accidentX, int accidentY) {

        //Set the map dimensions
        MAP_X = x;
        MAP_Y = y;

        //Create the map
        map = new int[x][y];

        //Define the car initial position
        carPosition[0] = 0;
        carPosition[1] = 0;
        carFacing = directions.EAST;

        //The signal colors initial state
        signal[0] = signalColors.RED;
        signal[1] = signalColors.GREEN;

        //Set the accident site randomly
        accidentSite[0] = accidentX;
        accidentSite[1] = accidentY;

    }

    //Get the car`s position
    public int[] getCarLocation() {
        return carPosition;
    }

    //Return the signal color
    public boolean getSignalColor(boolean goStraight) {

        //Update the car`s direction
        switch (carFacing) {
            case NORTH:
            case SOUTH:
                if (goStraight && signal[0] == signalColors.GREEN) { //Going Straight
                    return true;
                } else if (!goStraight && signal[1] == signalColors.GREEN) { //Turning
                    return true;
                }
                break;
            case EAST:
            case WEST:
                if (goStraight && signal[1] == signalColors.GREEN) { //Going Straight
                    return true;
                } else if (!goStraight && signal[0] == signalColors.GREEN) { //Turning
                    return true;
                }
                break;
        }

        return false;

    }

    //Return the bump flag
    public boolean getBump() {

        //If car has bumped
        if (bump) {

            //Reset variable
            bump = false;

            return true;

        }

        return false;

    }

    //Check if the car arrived in the accident site
    public boolean getReachedAccidentSite() {

        return carPosition[0] == accidentSite[0] && carPosition[1] == accidentSite[1];
    }

    //Turn Left 
    public void turnLeft() {

        //Update the car`s direction
        switch (carFacing) {

            case EAST:
                if (carPosition[1] < MAP_Y) {
                    carPosition[1]++;
                    carFacing = directions.NORTH;
                    return;
                }
                break;
            case WEST:
                if (carPosition[1] > 0) {
                    carPosition[1]--;
                    carFacing = directions.SOUTH;
                    return;
                }
                break;
            case NORTH:
                if (carPosition[0] > 0) {
                    carPosition[0]--;
                    carFacing = directions.WEST;
                    return;
                }
                break;
            case SOUTH:
                if (carPosition[0] < MAP_X) {
                    carPosition[0]++;
                    carFacing = directions.EAST;
                    return;
                }
                break;
        }

        //It has bump
        bump = true;

    }

    //Turn Right
    public void turnRight() {

        //Update the car`s direction
        switch (carFacing) {

            case EAST:
                if (carPosition[1] > 0) {
                    carPosition[1]--;
                    carFacing = directions.SOUTH;
                    return;
                }
                break;
            case WEST:
                if (carPosition[1] < MAP_Y) {
                    carPosition[1]++;
                    carFacing = directions.NORTH;
                    return;
                }
                break;
            case NORTH:
                if (carPosition[0] < MAP_X) {
                    carPosition[0]++;
                    carFacing = directions.EAST;
                    return;
                }
                break;
            case SOUTH:
                if (carPosition[0] > 0) {
                    carPosition[0]--;
                    carFacing = directions.WEST;
                    return;
                }
                break;
        }

        //It has bump
        bump = true;
    }

    //Go Straight
    public void goStraight() {

         changeSignal();
        
        //Update the car`s direction
        switch (carFacing) {

            case EAST:
                if (carPosition[0] < MAP_X) {
                    carPosition[0]++;
                    return;
                }
                break;
            case WEST:
                if (carPosition[0] > 0) {
                    carPosition[0]--;
                    return;
                }
                break;
            case NORTH:
                if (carPosition[1] < MAP_Y) {
                    carPosition[1]++;
                    return;
                }
                break;
            case SOUTH:
                if (carPosition[1] > 0) {
                    carPosition[1]--;
                    return;
                }
                break;
        }

        //It has bump
        bump = true;
    }

    //Wait at the signal
    public void waitSignal() {


    }

    //Test signal light
    public void changeSignal() {

        //Test signal light
        if (signal[0] == signalColors.GREEN) {
            signal[0] = signalColors.RED;
            signal[1] = signalColors.GREEN;
        } else {
            signal[0] = signalColors.GREEN;
            signal[1] = signalColors.RED;
        }

    }

    //Car has stopped its movement
    public boolean stop() {

        //Return if the accident site was reached
        return this.getReachedAccidentSite();

    }

    //Get the Accident Site - DEBUG MODE
    public int[] getAccidentSite() {

        return accidentSite;

    }

}
