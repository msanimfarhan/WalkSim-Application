import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class SpiralWalkerTest {
    static final float[] testMat1 = {0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f};
    static final MarkovChain testMC1 = new MarkovChain(new FloatMatrix(testMat1, 4));

    @Test
    void testWalk_CommonCase() {

                SpiralWalker walker = new SpiralWalker(testMC1);
        int Nsteps = 10; // Valid number of steps

        ArrayList<Coordinate> path = walker.walk(Nsteps);

        assertNotNull(path);
        assertEquals(2 * Nsteps, path.size(), "With N steps in a spiral, expected 2N coordinates on the path");
    }

    @Test
    void saveWalkToFile() {
    }
}