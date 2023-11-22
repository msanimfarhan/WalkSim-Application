import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Coordinate class.
 * 1. add()
 * 1a. test adding two positive coordinates
 * 1b. test adding positive coordinate to a negative coordinate
 * 1c. test adding the zero coordinate
 * 2. sub()
 * 2a. test subtracting two positive Coordinates
 * 2b. test subtracting a negative from a positive Coordinate
 * 2c. test subtracting the zero coordinate.
 * 3. scale()
 * 3a. scale a positive coordinate by a positive scale
 * 3b. scale a positive coordinate by a negative scale
 * 3c. scale a negative coordinate by a positive scale
 * 3d. scale a negative corodinate by a negative scale
 * 3e. scale a coordinate by 0.
 * 4. accumulate()
 * 4a. accumulate two positive coordinates
 * 4b. accumulate a zero coordinate
 * 4c. accumulate a negative coordinate
 */
class CoordinateTest {

    static final int testX1 = 1;
    static final int testY1 = 2;
    static final int testX2 = 10;
    static final int testY2 = 12;
    static final int testX3 = -5;
    static final int testY3 = -8;


    @org.junit.jupiter.api.Test
    void add_case1() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(testX2, testY2);
        Coordinate c = a.add(b);
        assertEquals(c.x, testX1 + testX2);
        assertEquals(c.y, testY1 + testY2);
    }

    @org.junit.jupiter.api.Test
    void add_case2() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(testX3, testY3);
        Coordinate c = a.add(b);
        assertEquals(c.x, testX1 + testX3);
        assertEquals(c.y, testY1 + testY3);
    }

    @org.junit.jupiter.api.Test
    void add_case3() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(0, 0);
        Coordinate c = a.add(b);
        assertEquals(c.x, testX1);
        assertEquals(c.y, testY1);
    }

    @org.junit.jupiter.api.Test
    void sub_case1() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(testX2, testY2);
        Coordinate c = a.sub(b);
        assertEquals(c.x, testX1 - testX2);
        assertEquals(c.y, testY1 - testY2);
    }

    @org.junit.jupiter.api.Test
    void sub_case2() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(testX3, testY3);
        Coordinate c = a.sub(b);
        assertEquals(c.x, testX1 - testX3);
        assertEquals(c.y, testY1 - testY3);
    }

    @org.junit.jupiter.api.Test
    void sub_case3() {
        Coordinate a = new Coordinate(testX1, testY1);
        Coordinate b = new Coordinate(0, 0);
        Coordinate c = a.sub(b);
        assertEquals(c.x, testX1);
        assertEquals(c.y, testY1);
    }

    @org.junit.jupiter.api.Test
    void scale_case1() {
        Coordinate a = new Coordinate(testX1, testY1);
        int s = 2;
        Coordinate c = a.scale(s);
        assertEquals(c.x, testX1 * s);
        assertEquals(c.y, testY1 * s);
    }


    @org.junit.jupiter.api.Test
    void scale_case2() {
        Coordinate a = new Coordinate(testX1, testY1);
        int s = -2;
        Coordinate c = a.scale(s);
        assertEquals(c.x, testX1 * s);
        assertEquals(c.y, testY1 * s);
    }


    @org.junit.jupiter.api.Test
    void scale_case3() {
        Coordinate a = new Coordinate(testX3, testY3);
        int s = 2;
        Coordinate c = a.scale(s);
        assertEquals(c.x, testX3 * s);
        assertEquals(c.y, testY3 * s);
    }

    @org.junit.jupiter.api.Test
    void scale_case4() {
        Coordinate a = new Coordinate(testX3, testY3);
        int s = -3;
        Coordinate c = a.scale(s);
        assertEquals(c.x, testX3 * s);
        assertEquals(c.y, testY3 * s);
    }

    @org.junit.jupiter.api.Test
    void scale_case5() {
        Coordinate a = new Coordinate(testX3, testY3);
        int s = 0;
        Coordinate c = a.scale(s);
        assertEquals(c.x, 0);
        assertEquals(c.y, 0);
    }

    @org.junit.jupiter.api.Test
    void accumulate_case1() {
        Coordinate acc = new Coordinate(testX1, testY1);
        Coordinate before = new Coordinate(acc.x, acc.y);
        Coordinate toAdd = new Coordinate(testX2, testY2);

        acc.accumulate(toAdd);
        Coordinate sum = before.add(toAdd);

        assertEquals(acc.x, sum.x);
        assertEquals(acc.y, sum.y);
    }

    @org.junit.jupiter.api.Test
    void accumulate_case2() {
        Coordinate acc = new Coordinate(testX1, testY1);
        Coordinate before = new Coordinate(acc.x, acc.y);
        Coordinate toAdd = new Coordinate(testX3, testY3);

        acc.accumulate(toAdd);
        Coordinate sum = before.add(toAdd);

        assertEquals(acc.x, sum.x);
        assertEquals(acc.y, sum.y);
    }

    @org.junit.jupiter.api.Test
    void accumulate_case3() {
        Coordinate acc = new Coordinate(testX1, testY1);
        Coordinate before = new Coordinate(acc.x, acc.y);
        Coordinate toAdd = new Coordinate(0, 0);

        acc.accumulate(toAdd);

        assertEquals(acc.x, before.x);
        assertEquals(acc.y, before.y);
    }
}