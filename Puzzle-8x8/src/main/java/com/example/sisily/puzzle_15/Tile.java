package com.example.sisily.puzzle_15;

/**
 * Created by sisily on 01/02/18.
 */

/** A tile of the puzzle. Each tile has a (unique) number. */
public class Tile {

    private final int number;

    /** Create a new tile that has the given number */
    public Tile(int number) {
        this.number = number;
    }

    /** Return the number of this tile. */
    public int number() {
        return number;
    }
}
