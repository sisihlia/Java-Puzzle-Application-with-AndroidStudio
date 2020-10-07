package com.example.sisily.puzzle_15;

import com.example.sisily.puzzle_15.HeuristicSearch.BreadthFS;
import com.example.sisily.puzzle_15.HeuristicSearch.Manhattan;
import com.example.sisily.puzzle_15.HeuristicSearch.Mismatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sisily on 01/02/18.
 */
/**
 * A puzzle frame consisting of <code>size</code> * <code>size</code>
 * positions where puzzle tiles can be placed.
 *
 * @see Position
 * @see Tile
 */
public class Board {

    /** Dimension of this board. This board will have
     *  <code>size</code> * <code>size</code> positions. */
    private final int size =3 ;

    /** Number of tile moves made so far. */
    private int numOfMoves;

    /** Positions of this board. */
    private final List<Position> positions;

    /** Listeners listening to board changes such as sliding of tiles. */
    private final List<BoardChangeListener> listeners;

    /** position to solve : Tiles number after shuffling */
    private String str;

    /** position goal */
    private String goal = "123456780";

    /** Directions to move the 0 tile to solve the puzzle */
    private List<String>movements=new ArrayList<>();

    /** all nodes until solved position */
    private List<String> arr = new ArrayList<>();

    /** To arrange tiles randomly. */
    private final static Random random = new Random();


    /** Create a new board of the given dimension. Initially, the tiles
     * are ordered with the blank tile as the last tile. */
    public Board(){
        listeners = new ArrayList<BoardChangeListener>();
        Position p;
        positions = new ArrayList<Position>(size * size);
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                if (x==size && y ==size){
                     p = new Position (x,y,this);
                }else {
                     p = new Position (x, y, (y - 1)* size + x, this);
                }

                positions.add(p);
            }
        }
        numOfMoves = 0;

    }


    /** Rearrange the tiles to create a new, solvable puzzle. */
    public String rearrange() {
        //String str;
        List<Integer> tileNumbers = new ArrayList<>();
        numOfMoves = 0;
        for (int i = 0; i < size*size; i++) {
            swapTiles();
        }

        for (int i=0; i<3; i++){
           for (int j=i; j<9; j+=3){
               if (positions.get(j).hasTile()) {
                   tileNumbers.add(positions.get(j).getTile().number());
                  //System.out.println("Position " + ""+ i + "" +" has tile number " + positions.get(i).getTile().number());
               } else {
                   tileNumbers.add(0);
                   //System.out.println ("Position " + "" +i + " has no tile");
               }
           }
        }

        System.out.println(Arrays.toString(tileNumbers.toArray()));
        str=(tileNumbers.toString().replaceAll("\\[|\\]|[,][ ]",""));

        return str;
    }

    /** Swap two tiles randomly. */
    private void swapTiles() {
        Position p1 = atPosition(random.nextInt(size) + 1, random.nextInt(size) + 1);
        Position p2 = atPosition(random.nextInt(size) + 1, random.nextInt(size) + 1);
        if (p1 != p2) {
            Tile t = p1.getTile();
            p1.setTile(p2.getTile());
            p2.setTile(t);
        }
    }

    /** Is the puzzle (current arrangement of tiles) solvable? */
    public boolean solvable() {
        // count the number of inversions, where an inversion is when
        // a tile precedes another tile with a lower number on it.
        int inversion = 0;
        for (Position p: positions) {
            Tile pt = p.getTile();
            for (Position q: positions) {
                Tile qt = q.getTile();
                if (p != q && pt != null && qt != null &&
                        indexOf(p) < indexOf(q) &&
                        pt.number() > qt.number()) {
                    inversion++;
                }
            }
        }
        final boolean isEvenSize = size % 2 == 0;
        final boolean isEvenInversion = inversion % 2 == 0;
        boolean isBlankOnOddRow = blank().getY() % 2 == 1;
        // from the bottom
        isBlankOnOddRow = isEvenSize ? !isBlankOnOddRow : isBlankOnOddRow;
        return (!isEvenSize && isEvenInversion) ||
                (isEvenSize && isBlankOnOddRow == isEvenInversion);
    }

    /** Return the place at the given indices. */
    public Position atPosition(int x, int y) {
        for (Position p: positions) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        //assert false : "precondition violation!";
        return null;
    }

    /** Slide the given tile, which is assumed to be slidable, and
     * notify the change to registered board change listeners, if any.
     */
     public void slide(Tile tile) {
        for (Position p: positions) {
            if (p.getTile() == tile) {
                final Position to = blank();
                to.setTile(tile);
                p.setTile(null);
                numOfMoves++;
               notifyTileSliding(p, to, numOfMoves);

                return;
            }
        }
    }

    /** Is the tile in the given place slidable? */
    public boolean slidable(Position place) {
        int x = place.getX();
        int y = place.getY();
        return isBlank(x - 1, y) || isBlank(x + 1, y)
                || isBlank(x, y - 1) || isBlank(x, y + 1);
    }

    /** Return the blank position. */
    public Position blank() {
        for (Position p: positions) {
            if (p.getTile() == null) {
                return p;
            }
        }
        //assert false : "should never reach here!";
        return null;
    }

    /** Is the position at the given indices empty? */
    private boolean isBlank(int x, int y) {
        return (0 < x && x <= size) && (0 < y && y <= size)
                && atPosition(x,y).getTile() == null;
    }

    /** Return the 1-based index of the given position when all the places
     * are arranged in row-major order. */
    private int indexOf(Position p) {
        return (p.getY() - 1) * size + p.getX();

    }

    /** Register the given listener to listen to board changes. */
    public void addBoardChangeListener(BoardChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /** Notify a tile sliding to registered board change listeners. */
    private void notifyTileSliding(Position from, Position to, int numOfMove) {
        for (BoardChangeListener listener: listeners) {
            listener.tileSlid(from, to, numOfMoves);
        }
    }

    /** Return all the places of this board. */
    public Iterable<Position> places() {
        return positions;
    }

    /** Return the dimension of this board. */
    public int size() {
        return size;
    }

    /** Return the list of direction to move the 0 tile */
    public List<String> getDirection (){
        String move;
        //String p1="208635147";
        //String p2="238605147";
    for (int i=arr.size()-1;i>0.;i--){
        //if(i==arr.size()-1){break;}

        int indexp1=arr.get(i).indexOf("0");
        int indexp2=arr.get(i-1).indexOf("0");
        if (indexp1>indexp2){// either UP or LEFT
            if (indexp2==indexp1-1) {move="LEFT"; movements.add(move);}
            else {move="UP"; movements.add(move);}
        }

        if (indexp1<indexp2){//either DOWN or RIGHT
            if (indexp1==indexp2-1) {move="RIGHT"; movements.add(move);}
            else {move="DOWN"; movements.add(move);}
        }
    }
        return movements;
    }

    /** Return the solution based on Manhattan */
    public String solveManhattan(){
        String str1 =  "208635147"; //initial state
       System.out.println("Manhattan");
        Manhattan man = new Manhattan (this.str,this.goal);
        String solution = man.findSolution();

        arr = man.getMovements();
        if (this.solvable()){
            List<String> p = this.getDirection();
            for (int i=0; i<p.size();i++){
                System.out.println("Move "+i + " " + p.get(i));
            }
        }
        return solution;
    }

    /** Return the solution based on BFS */
    public String solveBFS (){
        System.out.println("BFS");
       // String str1 =  "208635147"; //initial state
       BreadthFS bfs = new BreadthFS(this.str,this.goal);
        String solution = bfs.findSolution();
        arr = bfs.getMovements();
        if (this.solvable()){
            List<String> p = this.getDirection();
            for (int i=0; i<p.size();i++){
                System.out.println("here i= "+i + " " + p.get(i));
            }
        }
        return solution;

    }

    /** Return the solution based on Mismatch */
    public String solveMismatch () {
       // String str1 =  "208635147"; //initial state
        System.out.println("Mismatch");
        Mismatch mis = new Mismatch(this.str,this.goal);
        String solution = mis.findSolution();
        arr = mis.getMovements();
        if (this.solvable()){
            List<String> p = this.getDirection();
            for (int i=0; i<p.size();i++){
                System.out.println("here i= "+i + " " + p.get(i));
            }
        }
        return solution;

    }

    /** Return the fitness value of solution obtained.
     *  0 is solved. 1 is the most mixed up positions  */
    public float calFitnessFunc (String result){
        //String result =  "128503746";
        String goal =  "123456780";
        int fitness=0;
        String[] goalList = goal.split("");
        String[] resultList = result.split("");
        List<String> list1 = new ArrayList<String>(Arrays.asList(goalList));
        List<String> list2 = new ArrayList<String>(Arrays.asList(resultList));
        for (int i=0; i<list1.size();i++){
            if (!list1.get(i).equals(list2.get(i)))fitness++;
        }
        System.out.println ("Fitness value is " + String.format("%.02f", (float)fitness/9));
        return (float)fitness/9;
    }

    public static void main(String[] args) {
      Board b= new Board();
      b.solveManhattan();
    }

    /** To listen to board changes such as tile sliding. */
    public interface BoardChangeListener {

        /** Called when the tile located at the <code>from</code>
         * position was slid to the empty <code>to</code> place. Both positions
         * will be provided in new states; i.e., <code>from</code> will
         * be empty and <code>to</code> will be the tile moved. */
        void tileSlid(Position from, Position to, int numOfMoves);

    }


}
