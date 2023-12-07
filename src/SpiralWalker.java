import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class SpiralWalker  {
    private Coordinate curPos;
    private ArrayList<Coordinate> path;


    public static final int START_X = 0;
    public static final int START_Y = 0;
    public static final int STEP_SIZE = 1; //each step along the lattice moves this many units

    private MarkovChain mc;


    public SpiralWalker(MarkovChain chain) {
        mc = chain;
        curPos = new Coordinate(START_X, START_Y);
        path = new ArrayList<>();
    }


    public ArrayList<Coordinate> walk(int Nsteps) {
        int direction = 0; // 0: North, 1: East, 2: South, 3: West
        int stepsInCurrentDirection = 0;
        int stepsToChangeDirection = 1;
        int directionChanges = 0;

        path.clear();
        path.add(new Coordinate(curPos.x, curPos.y));

        for (int i = 0; i < Nsteps; ++i) {
            if (stepsInCurrentDirection < stepsToChangeDirection) {
                // Move in the current direction
                curPos = moveInDirection(curPos, direction);
                path.add(new Coordinate(curPos.x, curPos.y));
                stepsInCurrentDirection++;
            } else {
                // Change direction
                direction = (direction + 1) % 4;
                stepsInCurrentDirection = 0;
                directionChanges++;

                if (directionChanges % 2 == 0) {
                    stepsToChangeDirection++;
                }
            }
        }

        return path;
    }
    public void saveWalkToFile(String fname) throws IOException {
        PrintWriter writer = new PrintWriter(fname);

        if (fname.endsWith(".txt")) {
            for (Coordinate coord : path) {
                writer.println(String.format("(%d, %d)", coord.x, coord.y));
            }
        } else if (fname.endsWith(".dat")) {
            StringBuilder sb = new StringBuilder();
            for (Coordinate coord : path) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(coord.x).append(" ").append(coord.y);
            }
            writer.println(sb.toString());
        }
        writer.close();

    }
    private Coordinate moveInDirection(Coordinate position, int direction) {
        if (direction == 0) { // North
            return new Coordinate(position.x, position.y + 1);
        } else if (direction == 1) { // East
            return new Coordinate(position.x + 1, position.y);
        } else if (direction == 2) { // South
            return new Coordinate(position.x, position.y - 1);
        } else if (direction == 3) { // West
            return new Coordinate(position.x - 1, position.y);
        } else {
            throw new IllegalArgumentException("Invalid direction");
        }
    }


}
