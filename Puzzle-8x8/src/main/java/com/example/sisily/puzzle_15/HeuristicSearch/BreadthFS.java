package com.example.sisily.puzzle_15.HeuristicSearch;

/**
 * Created by sisily on 04/02/18.
 */



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BreadthFS {
        /*
         Breadth first search algorithm
         * declare openlist
         * add root node to our openlist
         * while openlist not empty do following loops:
         * a. retrieve then remove first node of our openlist
         * b. check status of retrieved node
         *      if it is the goal node then break loop and print solution
         *      if it is not goal node then:
         *      - expand retrieved node
         *      - ADD expanded node at THE END of our openlist
         *      - continue loop
         */

         /* Board positions index (because we use string)
         * 0 1 2
         * 3 4 5
         * 6 7 8
         *
         * if str = "135782460" then it correspondent to
         * 1 3 5
         * 7 8 2
         * 4 6 0
         *
         * with 0 as blank space
         *
         */

    String str = ""; // initial state
    String goal = ""; //goal state

    // openlist
    LinkedList <String> openList;

    /** declare the queue */
    Map<String,Integer> levelDepth;

    /** declare the state history */
    Map<String,String> stateHistory;

    int nodes = 0; //counter for node generation
    int limit = 150; //counter for limit
    int unique = -1;
    int newValue;//counter for level depth
    int a;

    String currState;

    /** solution found */
    boolean solution = false;//flag if solution exist or not

    /** List for every state to solve the puzzle */
    List<String> movements = new ArrayList<>();

    /** initiate the constructor */
    public BreadthFS(String str,String goal){
        openList = new LinkedList <String> ();
        levelDepth = new HashMap<String, Integer>();
        stateHistory = new HashMap<String,String>();
        this.str = str;
        this.goal = goal;
        addToOpenList(str,null);//add root
    }

    /** method to find the solution based on BFS */
    public String findSolution (){

        while (!openList.isEmpty()){

            currState = openList.removeFirst();//RETRIEVE then remove first node of our openlist

            if (currState.equals(goal)){ // check if current state is goal state
                solution = true;
                printSolution(currState);// print solutions
                break;
            }


            if (levelDepth.get(currState) == limit){//check if under limit
                solution = false;
                printSolution(currState);// print solutions
                break;
            }

            else {
                //expand currentstate then add expanded node to the of openlist

                a = currState.indexOf("0");// get index position of 0 (blank)


                //left
                while (a != 0 && a != 3 && a != 6){// if blank not in the left most column then it able move left
                    String nextState = currState.substring(0,a-1)+"0"+currState.charAt(a-1)+currState.substring(a+1);//swap blank with destination
                    addToOpenList(nextState, currState);//add expanded node to openlist
                    nodes++;
                    break;
                }

                //up
                while (a!=0 && a!=1 && a!=2){//if blank not in the very top of row then it able to move up
                    String nextState = currState.substring(0,a-3)+"0"+currState.substring(a-2,a)+currState.charAt(a-3)+currState.substring(a+1);//swap blank with destination
                    addToOpenList(nextState, currState);//add expanded node to openlist
                    nodes++; //nodes = nodes + 1; a node is being genereted add it to counter
                    break;
                }

                //right
                while(a != 2 && a != 5 && a != 8){// if blank not in the right most column then it able to move right
                    String nextState = currState.substring(0,a)+currState.charAt(a+1)+"0"+currState.substring(a+2);//swap blank with destination
                    addToOpenList(nextState, currState);//add expanded node to openlist
                    nodes++;
                    break;
                }

                //down
                while (a != 6 && a != 7 && a != 8) {// if blank not in the very bottom row then it able to move down
                    String nextState = currState.substring(0,a)+currState.substring(a+3,a+4)+currState.substring(a+1,a+3)+"0"+currState.substring(a+4);//swap blank with destination
                    addToOpenList(nextState, currState);//add expanded node to openlist
                    nodes++;
                    break;
                }

            }

        }

        if (solution){
            System.out.println("Solution Exist");
            System.out.println("State found is " +currState + " in " + levelDepth.get(currState)+" step(s)");
        }

        else {
            System.out.println("State unsolved  " +currState+ " in " + levelDepth.get(currState)+" step(s)");
            System.out.println("Solution not yet found!");
           // System.out.println("Solution not yet found! My suggestion are:");
           // System.out.println("1. Try to increse level depth limit ");
           // System.out.println("2. Maybe it is physically impossible");
        }
        return currState;

    }

    /** Returns the movements */
    public List<String> getMovements(){
        return movements;
    }

    /** add to the queue the new node */
    public void addToOpenList (String newState, String oldState){
        if(!levelDepth.containsKey(newState)){// check repeated state
            newValue = oldState == null ? 0 : levelDepth.get(oldState) + 1;
            unique ++;
            levelDepth.put(newState, newValue);
            openList.add(newState);//add node at THE END of openlist (breadthFS Algorithm)
            stateHistory.put(newState, oldState);
        }

    }

    /** print the solutions */
    public void printSolution (String currState){
        if (solution){
            System.out.println("Solution found in " +levelDepth.get(currState)+" step(s)");
            System.out.println("Node generated: "+ nodes);
            System.out.println("Unique Node generated: "+ unique);
            System.out.println("Solution found is " +currState + " in " + levelDepth.get(currState)+" step(s)");
        }
        else{
            System.out.println("Solution unsolved is " +currState+ " in " + levelDepth.get(currState)+" step(s)");
            System.out.println("Solution not found!");
            System.out.println("Depth Limit Reached!");
            System.out.println("Node generated: "+ nodes);
            System.out.println("Unique Node generated: "+ unique);
        }

        String traceState = currState;
        synchronized (this) {
        while (traceState != null) {
            movements.add(traceState);
            System.out.println(traceState + " at " + levelDepth.get(traceState));
            try{
                for(int z=0;z<9;z++){
                    System.out.print(" " + String.valueOf(traceState.charAt(z)) + " ");
                    if ((z+1) % 3 == 0){System.out.println();}
                }
            }
            catch (NullPointerException e) {}
            traceState = stateHistory.get(traceState);
        }}
        //System.exit(0); //break
    }
}



