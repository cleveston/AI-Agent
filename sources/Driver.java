
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//The Driver Class
public class Driver {

    //Map size
    private static final int MAP_X = 10;
    private static final int MAP_Y = 10;

    //Car Agent
    private static CarAgent car;

    //Environment 
    private static Environment environment;

    //Define Console Scanner
    private static final Scanner console = new Scanner(System.in);

    //Main method
    public static void main(String[] args) {

        //Default path type
        boolean pathPlanned = true;

        //Random accident site
        int accidentX = ThreadLocalRandom.current().nextInt(1, MAP_X + 1);
        int accidentY = ThreadLocalRandom.current().nextInt(1, MAP_Y + 1);

        //Check if the program will run in debug mode
        if (args.length > 0 && args[0].equals("-d")) {

            //While the user enters the right option
            while (true) {

                //Show the path type selection
                System.out.print("Please enter r for a random path or p for a planned path (r/p): ");

                //Get user selection
                String pathType = console.nextLine().toLowerCase();

                //Verify if the path plan changed
                if (pathType.equals("r")) {
                    pathPlanned = false;
                    System.out.println("Random Path Selected");
                    break;
                } else if (pathType.equals("p")) {
                    System.out.println("Planned Path Selected");
                    break;
                }

            }

            //Show the accident site location
            System.out.println("Accident Site: [" + accidentX + ", " + accidentY + "]");
        }

        System.out.println();
        System.out.println("Starting Simulation");
        System.out.println("--------------------------------------------------------");

        //Do 5 simulations for the same accident site
        for (int i = 1; i <= 5; i++) {

            //Create the environment
            environment = new Environment(MAP_X, MAP_Y, accidentX, accidentY);

            System.out.println("Simulation " + i + " - " + ((pathPlanned) ? "Planned Path" : "Random Path"));

            System.out.println();

            car = new CarAgent(pathPlanned, environment);

            //Start Car
            car.go();

            System.out.println("--------------------------------------------------------");
            System.out.println("Simulation " + i + " - " + ((!pathPlanned) ? "Planned Path" : "Random Path"));

            //Create the environment
            environment = new Environment(MAP_X, MAP_Y, accidentX, accidentY);

            //Run with other path plan
            car = new CarAgent(!pathPlanned, environment);

            //Start Car
            car.go();

            System.out.println("--------------------------------------------------------");

        }
    }

}
