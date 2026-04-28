package model;

import java.util.HashMap;
import java.util.Map;

// Holds 4 doors and room state
public class Room {
    
    public enum Direction{UP, DOWN, LEFT, RIGHT};


    private boolean myIsExit;
    private Map<Direction, Door> myDoors;


    /**
     * Create a room with 4 closed doors surrounding.
     */
    public Room() {
        myIsExit = false;
        myDoors = new HashMap<>();

        for(Direction d : Direction.values() ){
            myDoors.put(d, new Door() );
        }
    }

    /**
     * Set the door status in the specified direction.
     * @param theDirection
     * @param theStatus
     */
    public void setDoorStatus(Direction theDirection, Door.DoorStatus theStatus){
        myDoors.get(theDirection).setStatus(theStatus);
    }

    public void addDoor(Direction theDirection, Door theDoor){
        myDoors.put(theDirection, theDoor);
    }

    public Door getDoor(Direction theDirection){
        return myDoors.get(theDirection);
    }

    public String toString(){
        return "Door(Left = " + myDoors.get(Direction.LEFT) +
            ", Up = " + myDoors.get(Direction.UP) +
            ", Right = " + myDoors.get(Direction.RIGHT) +
            ", Down = " + myDoors.get(Direction.DOWN) + ")";
    }



    public static void main(String[] args) {

        Room r = new Room();

        System.out.println(r);
    }
}
