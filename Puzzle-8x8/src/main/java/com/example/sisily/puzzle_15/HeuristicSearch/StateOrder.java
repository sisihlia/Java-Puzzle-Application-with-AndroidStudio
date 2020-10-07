package com.example.sisily.puzzle_15.HeuristicSearch;

/**
 * Created by sisily on 04/02/18.
 */


public class StateOrder implements Comparable
{

    /**

     Constructs a state order with a given priority and description.

     @param aPriority the priority of state

     @param aDescription the description of state

     */
    /** declare the priority */
    private int priority;

    /** declare the desc */
    private String description;

    /** initiate the constructor */
    public StateOrder (int aPriority, String aDescription)

    {

        priority = aPriority;

        description = aDescription;

    }

    @Override
    public String toString()


    {


        return description;

    }

    /** Returns the value comparison */
    public int compareTo(Object otherObject)

    {

        StateOrder other = (StateOrder) otherObject;

        if (priority < other.priority) return -1;

        if (priority > other.priority) return 1;

        return 0;
    }


}

