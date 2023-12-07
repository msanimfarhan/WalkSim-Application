import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A main class for orchestrating a random walk.
 */
public class WalkSim {

    /**
     * The main method for a random walk simulation.
     * First reads a file which encodes a Markov chain's transition matrix,
     * and then performs a random walk.
     * Command line arguments:
     *    [0]: the file containing the FloatMatrix for the Markov chain
     *    [1]: the output file to store the path produced
     *    [2]: the number of steps to simulate
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int nSteps;
        int walkerType;
        while(true){
            System.out.println("Enter an integer");
            if(kb.hasNextInt()) {
                 nSteps = kb.nextInt();

                while(true) {
                    System.out.println("Enter an integer  walker type");

                    if (kb.hasNextInt()) {
                         walkerType = kb.nextInt();
                        break;
                    }
                }
                break;


            }

        }
        String exampleFile;
        while(true){
            System.out.println("Enter file name");
             exampleFile = kb.next();
            String s = "test_cases/";
            exampleFile = s+exampleFile;

            try {
                // Attempt to open the file
                Scanner scanner = new Scanner(new File(exampleFile));
                System.out.println("The file exists and was opened successfully.");

                // Close the scanner if the file is successfully opened
                scanner.close();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("The file does not exist.");
            }

        }


//        String exampleFile = "test_cases/example1.txt";
        System.out.println("Enter output file name");
       String outputFile = kb.next();
        String x = "test_cases/";
        outputFile = x+outputFile;

//        int nSteps = 200;
//        int walkerType = 0;


        int stepDuration = 30; //controls the speed of the animation

        if (args.length > 0) {
            exampleFile = args[0];
        }
        if (args.length > 1) {
            outputFile = args[1];
        }
        if (args.length > 2) {
            try {
                int steps = Integer.parseInt(args[2]);
                nSteps = steps;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not use args[2] since it was not an integer: " + args[2]);
                System.out.printf("Defaulting to %d steps.\n", nSteps);
            }
        }
        if (args.length > 3) {
            try {
                int type = Integer.parseInt(args[3]);
                walkerType = type;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not use args[3] since it was not an integer: " + args[3]);
                System.out.printf("Defaulting to walker type %d.\n", walkerType);
            }
        }

        String[] cardinals = {"N", "E", "S", "W"};
        FloatMatrix T1 = null;
        try {
            T1 = FloatMatrix.fromFile(exampleFile);
            assert T1.rows() == 4 : "Walker MarkovChain should have 4 states";
            System.out.println(T1.prettyString());
            MarkovChain mc = new MarkovChain(T1, cardinals);
            ArrayList<Coordinate> theWalk = null;
            if(walkerType == 0) {
                RandomWalker walker = new RandomWalker(mc);

                theWalk = walker.walk(nSteps);
                walker.saveWalkToFile(outputFile);
            }
            else if(walkerType == 1){
                SpiralWalker walker = new SpiralWalker(mc);

                theWalk = walker.walk(nSteps);
                walker.saveWalkToFile(outputFile);
            }else if(walkerType == 2){
                BreadCrumbWalker walker = new BreadCrumbWalker(mc);

                theWalk = walker.walk(nSteps);
                walker.saveWalkToFile(outputFile);

            }
            WalkFrame walkFrame = new WalkFrame();
            walkFrame.animatePath(theWalk, stepDuration);

        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not find the specified matrix file.");
            System.out.println(fnfe.toString());
            System.exit(1);

        } catch (IOException ioe) {
            System.out.println("Could not save walk to file: " + outputFile);
            System.out.println(ioe.getMessage());
            System.exit(1);

        }

    }
}
