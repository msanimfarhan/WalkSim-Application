
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the MarkovChain class.
 * 1. getState()
 * 1a. ensure state number is always within number of states given by matrix
 * 1b. test getState after a transition with a forced probability.
 *
 * 2. setState()
 * 2a. set state with a valid state number
 * 2b. set state with an invalid state number
 * 2c. set state edge case with state = 0
 * 2c. set state edge case with state = number of states
 *
 * 3. nextState()
 * 3a. with a forced probability, ensure transition occurs from one state to another.
 *
 * 4. getNumStates()
 * 4a. where there should be 2 states
 * 4b. where there should be 3 states
 * 4c. where there should be 1 state
 *
 * 5. isValidTransitionMatrix()
 * 5a. a valid transition matrix should return true
 * 5b. a transition matrix with valid sums but invalid dimensions
 * 5c. a transition matrix of size 1 (edge case)
 */
class MarkovChainTest {

    static final float[] testMat1 = {1.0f, 0.0f, 1.0f, 0.0f}; //Always transition to state 0
    static final float[] testMat2 = {0.5f, 0.5f, 0.5f, 0.5f};
    static final float[] testMat3 = {0.0f, 1.0f, 0.0f, 0.5f, 0.5f, 0.0f};
    static final float[] testMat4 = {1.0f};
    static final float[] testMat5 = {0.0f, 1.0f, 0.0f, 0.33f, 0.34f, 0.33f, 0.5f, 0.5f, 0.0f};

    @org.junit.jupiter.api.Test
    void getState_validrange() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        int curState = mc.getState();
        assertTrue(curState >= 0, "Current state should be non-negative.");
        assertTrue(curState < 2, "Current state should be less than 2 with a Transition matrix of 2 rows");
    }


    @org.junit.jupiter.api.Test
    void getState_transition() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        mc.nextState();
        int curState = mc.getState();
        assertEquals(curState, 0, "This chain should always transition to state 0");
        mc.nextState();
        curState = mc.getState();
        assertEquals(curState, 0, "This chain should always transition to state 0");
    }

    @org.junit.jupiter.api.Test
    void setState_pass() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        boolean ret = mc.setState(1);
        assertEquals(mc.getState(), 1, "After setting state to 1, it should be 1.");
        assertTrue(ret, "Setting the state to 1, out of 2 possible states, should return true.");
    }

    @org.junit.jupiter.api.Test
    void setState_fail() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        boolean ret = mc.setState(20);
        assertFalse(ret);
    }

    @org.junit.jupiter.api.Test
    void setState_edge1() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        boolean ret = mc.setState(0);
        assertTrue(ret);
    }


    @org.junit.jupiter.api.Test
    void setState_edge2() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        boolean ret = mc.setState(2);
        assertFalse(ret);
    }

    @org.junit.jupiter.api.Test
    void nextState() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat1, 2));
        mc.setState(1);
        mc.nextState();
        assertEquals(mc.getState(), 0); //should always return to state 0.
    }

    @org.junit.jupiter.api.Test
    void getNumStates_2() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat2, 2));
        assertEquals(mc.getNumStates(), 2);
    }

    @org.junit.jupiter.api.Test
    void getNumStates_1() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat4, 1));
        assertEquals(mc.getNumStates(), 1);
    }

    @org.junit.jupiter.api.Test
    void getNumStates_3() {
        MarkovChain mc = new MarkovChain(new FloatMatrix(testMat5, 3));
        assertEquals(mc.getNumStates(), 3);
    }


    @org.junit.jupiter.api.Test
    void IsValidTransitionMatrix_validones() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        boolean valid = MarkovChain.isValidTransitionMatrix(M);
        assertTrue(valid);
    }

    @org.junit.jupiter.api.Test
    void IsValidTransitionMatrix_validfloats() {
        FloatMatrix M = new FloatMatrix(testMat2, 2);
        boolean valid = MarkovChain.isValidTransitionMatrix(M);
        assertTrue(valid);
    }

    @org.junit.jupiter.api.Test
    void IsValidTransitionMatrix_rectCorrectSum() {
        //rectangular matrix but correct sums
        FloatMatrix M = new FloatMatrix(testMat3, 3);
        boolean valid = MarkovChain.isValidTransitionMatrix(M);
        assertFalse(valid);
    }

    @org.junit.jupiter.api.Test
    void IsValidTransitionMatrix_sizeone() {
        FloatMatrix M = new FloatMatrix(testMat4, 1);
        boolean valid = MarkovChain.isValidTransitionMatrix(M);
        assertTrue(valid);
    }



}