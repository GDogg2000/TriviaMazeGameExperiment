package model;

public class Player
{
    private int myRow;
    private int myCol;


    public Player(final int theRow, final int theCol){
        myRow = theRow;
        myCol = theCol;
    }
    public Player(){
        this(0, 0);
    }

    public int getMyRow() { return myRow;}
    public int getMyCol() { return myCol;}

    public void setPosition(int theRow, int theCol){
        myRow = theRow;
        myCol = theCol;
    }

    @Override
    public String toString(){
        return "Player(" + myRow + ", " + myCol + ")";
    }

    public static void main(String[] args){
        Player p = new Player(2, 3);

        System.out.println(p);
    }
}
