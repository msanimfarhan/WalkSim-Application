import java.awt.datatransfer.FlavorEvent;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the FloatMatrix class.
 *
 * 1. columns()
 * 1a. a column vector
 * 1b. a square matrix
 * 1c. rectangle matrix with columns > rows
 * 1d. rectangle matrix with columns < rows
 *
 * 2. rows()
 * 2a. a column vector
 * 2b. a square matrix
 * 2c. rectangle matrix with columns > rows
 * 2d. rectangle matrix with columns < rows
 *
 * 3. zero()
 * 3a. zero() on a newly allocated matrix
 * 3b. zero() on a square matrix
 * 3c. zero() on a rectangular matrix
 *
 * 4. get()
 * 4a,b,c,d: get the 4 corners of a 2x2 matrix
 * 4e: get() with negative row index.
 * 4f: get() with negative column index.
 * 4g: get() with out of bounds row index
 * 4h: get() with out of bounds column index
 *
 * 5. set()
 * 5a,b,c,d: set the 4 corners of a 2x2 matrix
 * 5e: set() with negative row index.
 * 5f: set() with negative column index.
 * 5g: set() with out of bounds row index
 * 5h: set() with out of bounds column index
 *
 * 6. multiply()
 * 6a. test common case of a two 2x2 matrix
 * 6b. test exception is thrown on invalid dimensions
 * 6c. test multiplication with identity matrix leaves product unchanged.
 * 6d. test multiplication with self to produce the square of a matrix
 * 6e. test multiplication with null other matrix
 *
 * 7. fromFile()
 * 7a. test common case of a 2x2 matrix
 * 7b. test exception thrown for malformed file
 * 7c. test exception thrown for file not found
 */
class FloatMatrixTest {

    static final float[] testMatSmall = {1.0f};
    static final float[] testMat1 = {1.0f, 2.0f, 3.0f, 4.0f};
    static final float[] testMat2 = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f};
    static final float[] testMat3 = {4.0f, 3.0f, 2.0f, 1.0f};
    static final float[] prodMat1by3 = {8.0f, 5.0f, 20.0f, 13.0f};
    static final float[] testMat1_sq = {7.0f, 10.0f, 15.0f, 22.0f};
    static final float[] I_2 = {1.0f, 0.0f, 0.0f, 1.0f};

    void assertEqualsFloat(float expected, float actual) {
        assertTrue(Math.abs(expected-actual) < FloatMatrix.EPSILON);
    }

    @org.junit.jupiter.api.Test
    void columns_one() {
        FloatMatrix M = new FloatMatrix(testMatSmall, 1);
        assertEquals(1, M.columns());
    }

    @org.junit.jupiter.api.Test
    void columns_square() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        assertEquals(2, M.columns());
    }

    @org.junit.jupiter.api.Test
    void columns_wide() {
        FloatMatrix M = new FloatMatrix(testMat2, 3);
        assertEquals(3, M.columns());
    }

    @org.junit.jupiter.api.Test
    void columns_narrow() {
        FloatMatrix M = new FloatMatrix(testMat2, 2);
        assertEquals(2, M.columns());
    }

    @org.junit.jupiter.api.Test
    void rows_one() {
        FloatMatrix M = new FloatMatrix(testMatSmall, 1);
        assertEquals(1, M.rows());
    }

    @org.junit.jupiter.api.Test
    void rows_square() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        assertEquals(2, M.rows());
    }

    @org.junit.jupiter.api.Test
    void rows_wide() {
        FloatMatrix M = new FloatMatrix(testMat2, 3);
        assertEquals(2, M.rows());
    }

    @org.junit.jupiter.api.Test
    void rows_narrow() {
        FloatMatrix M = new FloatMatrix(testMat2, 2);
        assertEquals(3, M.rows());
    }

    @org.junit.jupiter.api.Test
    void zero_fromEmpty() {
        FloatMatrix M = new FloatMatrix(10);
        M.zero();
        for (int i = 0; i < M.rows(); ++i) {
            for (int j = 0; j < M.columns(); ++j) {
                assertEqualsFloat(0, M.get(i, j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void zero_fromSquare() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        M.zero();
        for (int i = 0; i < M.rows(); ++i) {
            for (int j = 0; j < M.columns(); ++j) {
                assertEqualsFloat(0, M.get(i, j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void zero_fromRectangle() {
        FloatMatrix M = new FloatMatrix(testMat2, 2);
        M.zero();
        for (int i = 0; i < M.rows(); ++i) {
            for (int j = 0; j < M.columns(); ++j) {
                assertEqualsFloat(0, M.get(i, j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void get_test1() {
        FloatMatrix M = new FloatMatrix(testMat1,2);
        assertEqualsFloat(1.0f, M.get(0, 0)); // First row, first column
    }

    @org.junit.jupiter.api.Test
    void get_test2() {
        FloatMatrix M = new FloatMatrix(testMat1,2);
        assertEqualsFloat(2.0f, M.get(0, 1)); // First row, second column
    }

    @org.junit.jupiter.api.Test
    void get_test3() {
        FloatMatrix M = new FloatMatrix(testMat1,2);
        assertEqualsFloat(3.0f, M.get(1, 0)); // Second row, first column
    }

    @org.junit.jupiter.api.Test
    void get_test4() {
        FloatMatrix M = new FloatMatrix(testMat1,2);
        assertEqualsFloat(4.0f, M.get(1, 1)); // Second row, second column
    }

    @org.junit.jupiter.api.Test
    void get_test5() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            float value = M.get(-1, 1);
            fail("Expected IndexOutOfBoundsException but got value: " + value);
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void get_test6() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            float value = M.get(1, -1);
            fail("Expected IndexOutOfBoundsException but got value: " + value);
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void get_test7() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            float value = M.get(3, 1);
            fail("Expected IndexOutOfBoundsException but got value: " + value);
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void get_test8() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            float value = M.get(1, 3);
            fail("Expected IndexOutOfBoundsException but got value: " + value);
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void set_test1() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        M.set(0, 0, 5.0f);
        assertEquals(5.0f, M.get(0, 0));
    }

    @org.junit.jupiter.api.Test
    void set_test2() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        M.set(0, 1, 6.0f);
        assertEquals(6.0f, M.get(0, 1));
    }

    @org.junit.jupiter.api.Test
    void set_test3() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        M.set(1, 0, 7.0f);
        assertEquals(7.0f, M.get(1, 0));
    }

    @org.junit.jupiter.api.Test
    void set_test4() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        M.set(1, 1, 8.0f);
        assertEquals(8.0f, M.get(1, 1));
    }

    @org.junit.jupiter.api.Test
    void set_test5() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            M.set(-1, 1, 10.0f);
            fail("Expected IndexOutOfBoundsException but got value: " + M.get(-1, 1));
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void set_test6() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            M.set(1, -1, 10.0f);
            fail("Expected IndexOutOfBoundsException but got value: " + M.get(1, -1));
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void set_test7() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            M.set(3, 1, 10.0f);
            fail("Expected IndexOutOfBoundsException but got value: " + M.get(3, 1));
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void set_test8() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        try {
            M.set(1, 3, 10.0f);
            fail("Expected IndexOutOfBoundsException but got value: " + M.get(1, 3));
        } catch (IndexOutOfBoundsException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void multiply_testCommon() {
        FloatMatrix M1 = new FloatMatrix(testMat1, 2);
        FloatMatrix M2 = new FloatMatrix(testMat3, 2);
        FloatMatrix exp = new FloatMatrix(prodMat1by3, 2);

        FloatMatrix result = M1.multiply(M2);

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                assertEqualsFloat(exp.get(i, j), result.get(i, j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void multiply_badDimensions() {
        FloatMatrix M1 = new FloatMatrix(testMat1, 2);
        FloatMatrix M2 = new FloatMatrix(testMat2, 2);
        try {
            FloatMatrix result = M1.multiply(M2);
            fail("Expected ArithmeticException but got result: " + result);
        } catch (ArithmeticException e) {
            // This exception is expected
        }
    }

    @org.junit.jupiter.api.Test
    void multiply_identity() {
        FloatMatrix M = new FloatMatrix(testMat1_sq, 2);
        FloatMatrix I = new FloatMatrix(I_2, 2);

        FloatMatrix result = M.multiply(I);
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                assertEqualsFloat(result.get(i,j), M.get(i,j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void multiply_self() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);
        FloatMatrix prod = M.multiply(M);

        FloatMatrix expected = new FloatMatrix(testMat1_sq, 2);
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                assertEqualsFloat(prod.get(i,j), expected.get(i,j));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void multiply_null() {
        FloatMatrix M = new FloatMatrix(testMat1, 2);

        try {
            FloatMatrix prod = M.multiply(null);
            fail("Expected ArithmeticException to be thrown on invalid multiply parameter, but got: " + prod);
        } catch (ArithmeticException e) {
            //Exception expected
        }
    }


    @org.junit.jupiter.api.Test
    void fromFile_goodFile() {
        try {
            FloatMatrix M = FloatMatrix.fromFile("test/inputs/good.txt");
            assertEquals(2, M.columns());
            assertEquals(1.0f, M.get(0,0));
        } catch (FileNotFoundException e) {
            fail("FileNotFound exception occurred and was unexpected.");
        }
    }

    @org.junit.jupiter.api.Test
    void fromFile_malformedFile() {
        try {
            FloatMatrix M = FloatMatrix.fromFile("test/inputs/malformed.txt");
            fail("FloatMatrix.fromFile() should have failed to find the file.");
        } catch (RuntimeException e) {
            //Exception expected.
        } catch (FileNotFoundException e) {
            fail("FileNotFound exception occurred and was unexpected.");
        }
    }

    @org.junit.jupiter.api.Test
    void fromFile_notFound() {
        try {
            FloatMatrix M = FloatMatrix.fromFile("asldijfbqwoe8rt.qwe9c");
            fail("FloatMatrix.fromFile() should have failed to find the file.");
        } catch (FileNotFoundException e) {
            //Exception expected.
        }
    }
}