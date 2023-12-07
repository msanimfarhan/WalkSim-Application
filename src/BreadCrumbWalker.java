
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class BreadCrumbWalker extends RandomWalker {

    private MarkovChain mc;
    private Coordinate curPos;
    private ArrayList<Coordinate> path;


    public BreadCrumbWalker(MarkovChain chain) {
            super(chain);
        }


        @Override
        public ArrayList<Coordinate> walk(int Nsteps) {
            // Perform the initial random walk
            if (Nsteps <= 0) {
                ArrayList<Coordinate> path = new ArrayList<>();
                path.add(new Coordinate(START_X, START_Y));
                return path;
            }

            ArrayList<Coordinate> path = super.walk(Nsteps);

            // Copy the forward path except the last element (to avoid duplication)
            ArrayList<Coordinate> reversePath = new ArrayList<>(path.subList(0, path.size() - 1));

            // Reverse the path to walk back
            Collections.reverse(reversePath);

            // Combine the forward and reverse paths
            path.addAll(reversePath);

            return path;
        }

        public void saveWalkToFile(String fname) throws IOException {
            PrintWriter writer = new PrintWriter(fname);

            if (this.path == null || this.path.isEmpty()) {
                // Handle the empty path scenario
                // For example, write a message to the file or just return
                 writer = new PrintWriter(fname);
                writer.println("No path available or path is empty.");
                writer.close();
                return;
            }
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

//            for (Coordinate coord : path) {
//                writer.println(String.format("(%d, %d)", coord.x, coord.y));
//            }
//
//            writer.close();
        }


        // The saveWalkToFile method would be the same as in RandomWalker
    }

