import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BreadCrumbWalkerTest {
    static final float[] testMat1 = {0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f};
    static final MarkovChain testMC1 = new MarkovChain(new FloatMatrix(testMat1, 4));


    @Test
    void walk_testCommon() {
        RandomWalker walker = new BreadCrumbWalker(testMC1);
        int Nsteps = 10;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(2 * Nsteps + 1, path.size(), "With N steps, expected 2N+1 coordinates on the path");
        assertEquals(path.get(0), path.get(path.size() - 1), "Path should start and end at the same point");
    }

    @Test
    void walk_testZeroSteps() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        int Nsteps = 0;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size()-1,0, "Expected empty path with negative steps.");
    }




    @Test
    void testSaveWalkToFileValidPath() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        String validPath = "test_output.txt"; // Provide a valid file name
        walker.walk(10); // Generate a path

        assertDoesNotThrow(() -> walker.saveWalkToFile(validPath));
        File outputFile = new File(validPath);
        assertTrue(outputFile.exists());

        // Additional assertions to check file content can be added here

        // Clean up
        outputFile.delete();
    }
    @Test
    void testSaveWalkToFileInvalidPath() {
        BreadCrumbWalker walker = new BreadCrumbWalker(testMC1);
        walker.walk(10); // Generate a path
        String invalidPath = "non_existent_directory/test_output.txt";

        assertThrows(IOException.class, () -> walker.saveWalkToFile(invalidPath));
    }

    @Test
    void saveWalkToFile_Exception() {
        RandomWalker walker = new RandomWalker(testMC1);
        String fakePath = "foobarbaddirectory" + File.separator
                + "definitelynotarealdirectory123905" + File.separator
                + "testFile.txt";
        try {
            walker.saveWalkToFile(fakePath);
            fail("Should have failed trying to write to the fake path: " + fakePath);
        } catch (IOException e) {
            //Exception expected
        }
    }



}
