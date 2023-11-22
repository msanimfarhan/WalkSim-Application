
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RandomWalker class.
 * These are quite basic as the randomness makes unit tests challenging.
 * See component tests with WalkSim.
 *
 * 1. walk()
 * 1a. common case: ensure walk path is the correct length for number of steps.
 * 1b. edge case: walk 0 steps.
 * 1c: error case: walk negative steps.
 *
 * 2. saveWalkToFile()
 * 2a. common case, with a correct file path.
 * 2b. exceptional case, should throw IOException for bad file path.
 */
public class RandomWalkerTest {

    static final float[] testMat1 = {0.25f, 0.25f, 0.25f, 0.25f,
                                     0.25f, 0.25f, 0.25f, 0.25f,
                                     0.25f, 0.25f, 0.25f, 0.25f,
                                     0.25f, 0.25f, 0.25f, 0.25f};
    static final MarkovChain testMC1 = new MarkovChain(new FloatMatrix(testMat1, 4));

    @Test
    void walk_testCommon() {
        RandomWalker walker = new RandomWalker(testMC1);
        int Nsteps = 10;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(), Nsteps + 1, "With N steps, expected N+1 endpoints on the path");
    }

    @Test
    void walk_testZeroSteps() {
        RandomWalker walker = new RandomWalker(testMC1);
        int Nsteps = 0;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(),0, "Expected empty path with negative steps.");
    }

    @Test
    void walk_testNegSteps() {
        RandomWalker walker = new RandomWalker(testMC1);
        int Nsteps = -1;

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(path.size(),0, "Expected empty path with negative steps.");
    }


    @Test
    void saveWalkToFile_Common() {
        RandomWalker walker = new RandomWalker(testMC1);
        String path = "_saveWalkToFile_Common_UnitTest.txt";
        try {
            File fp = new File(path);
            assertFalse(fp.exists(), "Pre-condition: file should not yet exist.");

            walker.saveWalkToFile(path);

            assertTrue(fp.exists(), "File should exist after writing to it with no IOExeception.");
            assertTrue(fp.delete(), "Expected to be able to delete file after writing to it.");
        } catch (IOException e) {
            fail("Exception occurred while trying to save walk to the file: " + path);
        }
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
