/**
 * The Coordinate class encodes integer pairs representing
 * Cartesian coordinates on a two-dimensional integer lattice.
 * @author Alex Brandt; abrandt@dal.ca
 */
public class Coordinate {
    int x;
    int y;

    /**
     * Construct a new Coordinate pair with coordinates (x,y)
     * @param x: the first coorindate
     * @param y: the second coordinate
     */
    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add two Coordinate objects together, component-wise, returning
     * a new Coordinate.
     * @param other: the other Coordinate pair
     * @return the sum of this and other.
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(other.x + this.x, other.y + this.y);
    }

    /**
     * Add the Coordinate other to this coorindate, component-wise,
     * setting this Coordinate to the resulting sum.
     * @param other: the Coordinate pair to add to this Coordinate
     */
    public void accumulate(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Compute the difference, component-wise, between this Coordinate and the other Coordinate,
     * returning a new Coordinate which is the difference.
     * This Coordinate is the minuend and the other Coordinate is the subtrahend.
     * @param other: the subtrahend Coordinate
     * @return the difference between this and other.
     */
    public Coordinate sub(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiply this Coordinate, component-wise, by a scalar,
     * and return the new, scaled, Coordinate.
     * @param scale: the scalar by which to scale this Coordinate
     * @return the scaled Coordinated
     */
    public Coordinate scale(int scale) {
        return new Coordinate(this.x*scale, this.y*scale);
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
    }

}