package com.example.sisily.puzzle_15;

/**
 * Created by sisily on 01/02/18.
 */

/**
 * A Position in a puzzle board. Each place has a pair of 1-based
 * indices---<code>x</code> for column and <code>y</code> for
 * row---that uniquely identify it in the board. A position can be
 * occupied by a tile.
 *
 *
 */
public class Position {

    /** 1-based column index of this position. */
    private final int x;

    /** 1-based row index of this position. */
    private final int y;

    /** Tile currently placed at this position. */
    private Tile tile;

    /** Board which this position belongs to. */
    private Board board;

    /** Create a new position with the given indices for the given board. */
    public Position (int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    /** Create a new position with the given indices and a tile marked with
     * the given number for the given board. */
    public Position (int x, int y, int number, Board board) {
        this(x, y, board);
        tile = new Tile(number);
    }

    /** Return the 1-based column index of this position. */
    public int getX() {
        return x;
    }

    /** Return the 1-based row index of this position. */
    public int getY() {
        return y;
    }

    /** Does this position have a tile at it? */
    public boolean hasTile() {
        return tile != null;
    }

    /** Return the tile placed in this position; null is returned if no
     * tile is placed. */
    public Tile getTile() {
        return tile;
    }

    /** Position the given tile in this position. */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /** Is the tile in this position slidable? Return false if this position
     * is empty, i.e., no tile is placed. */
    public boolean slidable() {
        return hasTile() && board.slidable(this);
    }

    public void slide() {
        board.slide(getTile());
    }
}
