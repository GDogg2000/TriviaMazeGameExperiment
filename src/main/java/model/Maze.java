package model;

// 2D Grid, logic, and solvability
public class Maze
{
    public static int SIZE = 6;

    Room[][] myMaze;
    Player myPlayer;


    public Maze(){

        myPlayer = new Player(0, 0);


        myMaze = new Room[SIZE][SIZE];

        // now initialize each maze element with a default room
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                Room room = new Room();
                myMaze[row][col] = room;

                // These are the boundary doors and are not shared
                if(row == 0){
                    room.setDoorStatus(Room.Direction.UP, Door.DoorStatus.LOCK);
                }
                if(row == SIZE - 1){
                    room.setDoorStatus(Room.Direction.DOWN, Door.DoorStatus.LOCK);
                }
                if(col == 0){
                    room.setDoorStatus(Room.Direction.LEFT, Door.DoorStatus.LOCK);
                }
                if(col == SIZE - 1){
                    room.setDoorStatus(Room.Direction.RIGHT, Door.DoorStatus.LOCK);
                }
            }
        }


        // Now, we need to get internal doors to be shared
        for(int row = 1; row < SIZE; row++){
            for(int col = 0; col < SIZE - 1; col++){

                myMaze[row][col].addDoor(Room.Direction.UP,
                    myMaze[row-1][col].getDoor(Room.Direction.DOWN));
                myMaze[row][col].addDoor(Room.Direction.RIGHT,
                    myMaze[row][col+1].getDoor(Room.Direction.LEFT));
            }

            myMaze[row][SIZE - 1].addDoor(Room.Direction.UP,
                myMaze[row-1][SIZE - 1].getDoor(Room.Direction.DOWN));
        }

        //Still more to do -- got to get in first row doors to horizontal rooms
        for(int col = 0; col < SIZE - 1; col++){
            myMaze[0][col].addDoor(Room.Direction.RIGHT,
                myMaze[0][col+1].getDoor(Room.Direction.LEFT));
        }
    }

    public Player getPlayer(){
        return myPlayer;
    }

    public Room getRoom(int theRow, int theCol){
        return myMaze[theRow][theCol];
    }

    public char[][] getConsoleMap(){
        final char ROOM_SYMBOL = ' ';
        final char PLAYER_SYMBOL = 'P';
        int displaySize = 2*SIZE + 1;

        char[][] theMap = new char[displaySize][displaySize];



        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){

                Room room = myMaze[row][col];
                int displayRow = 2*row + 1;  // row where this room will be displayed
                int displayCol = 2*col + 1;

                theMap[displayRow - 1][displayCol] = getConsoleDoorChar(room.getDoor(Room.Direction.UP));
                theMap[displayRow + 1][displayCol] = getConsoleDoorChar(room.getDoor(Room.Direction.DOWN));
                theMap[displayRow][displayCol - 1] = getConsoleDoorChar(room.getDoor(Room.Direction.LEFT));
                theMap[displayRow][displayCol + 1] = getConsoleDoorChar(room.getDoor(Room.Direction.RIGHT));
                theMap[displayRow - 1][displayCol - 1] = 'W';
                theMap[displayRow - 1][displayCol + 1] = 'W';
                theMap[displayRow + 1][displayCol - 1] = 'W';
                theMap[displayRow + 1][displayCol + 1] = 'W';



                theMap[displayRow][displayCol] = ROOM_SYMBOL;
            }
        }

        for(int col = 0; col < displaySize; col++){
            theMap[0][col] = 'W';
            theMap[displaySize - 1][col] = 'W';
        }

        for(int row = 0; row < displaySize; row++){
            theMap[row][0] = 'W';
            theMap[row][displaySize - 1] = 'W';
        }

        theMap[ getDisplayRow( myPlayer.getMyRow() ) ][ getDisplayCol(myPlayer.getMyCol()) ] = PLAYER_SYMBOL;

        return theMap;
    }

    private int getDisplayRow(int theMazeRow){
        return 2 * theMazeRow + 1;
    }

    private int getDisplayCol(int theMazeCol){
        return 2 * theMazeCol + 1;
    }

    private char getConsoleDoorChar(Door d){
        if(d.getStatus() == Door.DoorStatus.OPEN){
            return 'O';
        }
        else if(d.getStatus() == Door.DoorStatus.CLOSE){
            return 'C';
        }
        else if(d.getStatus() == Door.DoorStatus.LOCK){
            return 'L';
        }
        else{
            return 'E';  //Error
        }

    }

    public void printMazeToConsole(){
        char[][] theMaze = this.getConsoleMap();

        for(int row = 0; row < 2*SIZE + 1; row++){
            System.out.print("\t");
            for(int col = 0; col < 2*SIZE + 1; col++){
                System.out.print(theMaze[row][col] + "\t");
            }
            System.out.println();
        }
    }

    public void resetCursor(){
        int N = 2 * SIZE + 1;
        System.out.print("\033[" + N + "F");
    }

    public static void main(String[] args)
    {

        Maze aMaze = new Maze();

        System.out.println("Maze:");

        aMaze.printMazeToConsole();


        try {
            // Pause for 2 seconds (2000 milliseconds)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Restore interrupted status
            Thread.currentThread().interrupt();
        }

        aMaze.resetCursor();
        aMaze.getPlayer().setPosition(1, 0);
        aMaze.getRoom(0, 0).getDoor(Room.Direction.DOWN).setStatus(Door.DoorStatus.OPEN);
        aMaze.getRoom(0, SIZE-1).getDoor(Room.Direction.LEFT).setStatus(Door.DoorStatus.OPEN);
        aMaze.getRoom(SIZE-1, 0).getDoor(Room.Direction.UP).setStatus(Door.DoorStatus.OPEN);
        aMaze.getRoom(SIZE -1, SIZE - 1).getDoor(Room.Direction.UP).setStatus(Door.DoorStatus.OPEN);
        aMaze.getRoom(SIZE -1, SIZE - 1).getDoor(Room.Direction.LEFT).setStatus(Door.DoorStatus.OPEN);

        System.out.println();

        aMaze.printMazeToConsole();

        System.out.println( aMaze.getRoom(0,0) );
        System.out.println( aMaze.getRoom(1,0) );

        // 1st row, last two columns share door
        System.out.print("First row, last two rooms share door:  ");
        Room r1 = aMaze.getRoom(0, SIZE - 2);  // room on left
        Room r2 = aMaze.getRoom(0, SIZE - 1);  // room on right
        System.out.println(r1.getDoor(Room.Direction.RIGHT) == r2.getDoor(Room.Direction.LEFT) );

        System.out.print("Last row, last two rooms share door:  ");
        r1 = aMaze.getRoom(SIZE - 1, SIZE - 2);  // room on left
        r2 = aMaze.getRoom(SIZE - 1, SIZE - 1);  // room on right
        System.out.println(r1.getDoor(Room.Direction.RIGHT) == r2.getDoor(Room.Direction.LEFT) );

        System.out.print("First column, bottom two rooms share door:  ");
        r1 = aMaze.getRoom(SIZE - 2, 0);  // room on top
        r2 = aMaze.getRoom(SIZE - 1, 0);    // room on bottom
        System.out.println(r1.getDoor(Room.Direction.DOWN) == r2.getDoor(Room.Direction.UP) );

        System.out.print("Last column, bottom two rooms share door:  ");
        r1 = aMaze.getRoom(SIZE - 2, SIZE - 1);  // room on top
        r2 = aMaze.getRoom(SIZE - 1, SIZE - 1); // room on bottom
        System.out.println(r1.getDoor(Room.Direction.DOWN) == r2.getDoor(Room.Direction.UP) );




    }
}
