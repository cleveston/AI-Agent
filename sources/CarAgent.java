
import java.util.concurrent.ThreadLocalRandom;

//The CarAgent Class
public class CarAgent {

    //Environment 
    private final Environment environment;

    //Bump counter
    private int totalBumps = 0;

    //Time elapsed
    private int time = 0;

    //The possible actions
    private static enum action {
        STRAIGHT,
        LEFT,
        RIGHT,
        WAITSIGNAL
    };

    //Movement plan
    private final boolean planned;

    //Next action
    private action nextAction = null;

    //Previous action
    private action previousAction = null;

    //Previous action
    private action plannedAction = null;

    //The Constructor Method
    public CarAgent(boolean planned, Environment env) {
        this.planned = planned;
        this.environment = env;
    }

    //Start the car
    public void go() {

        //Execute while the accident is not found
        while (!environment.getReachedAccidentSite()) {

            //Car has bump flag
            boolean bump = false;

            //Verify if car has bump
            if (environment.getBump()) {
                System.out.println("Car has bumped.");

                //Increment time in 10s
                time += 10;

                //Car has bump
                bump = true;

                //Increment bump counter
                totalBumps++;

            }

            //Choose the movement plan
            if (planned) {
                nextAction = planned(bump);
            } else {
                nextAction = random();
            }

            //Get car location
            int[] carLocation = environment.getCarLocation();

            //Show the car location
            System.out.println("Time: " + time + " - Car Location: " + carLocation[0] + ", " + carLocation[1] + " - Next Action: " + nextAction);

            //Execute the action in the environment
            switch (nextAction) {

                case STRAIGHT:
                    environment.goStraight();
                    time += 3;
                    break;

                case LEFT:
                    environment.turnLeft();
                    time += 4;
                    break;

                case RIGHT:
                    environment.turnRight();
                    time += 4;
                    break;

                case WAITSIGNAL:
                    environment.waitSignal();
                    time += 1;
                    break;

            }
        }

        //Print statistics
        System.out.println();
        System.out.println("Results");
        System.out.println("--------------------------------------------------------");

        //Stop the car
        if (environment.stop()) {

            //Get car location
            int[] carLocation = environment.getCarLocation();

            //Show the accident site location
            System.out.println("Accident Site Found: [" + carLocation[0] + ", " + carLocation[1] + "]");

        } else {

            //Show statistics
            System.out.println("Car could not reach the Accident Site");

        }

        //Show statistics
        System.out.println("Time taken: " + time + " sec");

        //Show the bump counter
        System.out.println("Bumps: " + totalBumps);

        System.out.println();

    }

//Random movement
    private action random() {

        //Verify is car has had to stop at the signal
        if (previousAction != null) {

            action actionTemp = previousAction;
            previousAction = null;
            return actionTemp;

        }

        //Random an action
        int option = ThreadLocalRandom.current().nextInt(0, 3);

        switch (option) {

            //Go Straight
            case 0:

                //Get signal
                if (!environment.getSignalColor(true)) {
                    previousAction = action.STRAIGHT;
                    return action.WAITSIGNAL;
                }

                return action.STRAIGHT;

            //Turn Left
            case 1:

                //Get signal
                if (!environment.getSignalColor(false)) {
                    previousAction = action.LEFT;
                    return action.WAITSIGNAL;
                }

                return action.LEFT;

            //Turn Right
            case 2:

                //Get signal
                if (!environment.getSignalColor(false)) {
                    previousAction = action.RIGHT;
                    return action.WAITSIGNAL;
                }

                return action.RIGHT;

        }

        return action.STRAIGHT;

    }

    //Planned movement
    private action planned(boolean bump) {

        //Verify is car has had to stop at the signal
        if (previousAction != null) {

            action actionTemp = previousAction;
            previousAction = null;
            return actionTemp;

        }

        if (plannedAction == action.LEFT) {

            plannedAction = null;

            //Get signal
            if (!environment.getSignalColor(false)) {
                previousAction = action.LEFT;
                return action.WAITSIGNAL;
            }

            return action.LEFT;

        } else if (plannedAction == action.RIGHT) {

            plannedAction = null;

            //Get signal
            if (!environment.getSignalColor(false)) {
                previousAction = action.RIGHT;
                return action.WAITSIGNAL;
            }

            return action.RIGHT;

        } else if (bump && totalBumps % 2 == 1) {

            plannedAction = action.LEFT;

            //Get signal
            if (!environment.getSignalColor(false)) {
                previousAction = action.LEFT;
                return action.WAITSIGNAL;
            }

            return action.LEFT;

        } else if (bump && totalBumps % 2 == 0) {

            plannedAction = action.RIGHT;

            //Get signal
            if (!environment.getSignalColor(false)) {
                previousAction = action.RIGHT;
                return action.WAITSIGNAL;
            }

            return action.RIGHT;
        } else {

            //Get signal
            if (!environment.getSignalColor(true)) {
                previousAction = action.STRAIGHT;
                return action.WAITSIGNAL;
            }
            return action.STRAIGHT;

        }

    }

}
