package model;

// Open, close, locked
public class Door
{
    public enum DoorStatus {OPEN, CLOSE, LOCK};

    private DoorStatus status;

    public Door(){
        status = DoorStatus.CLOSE;
    }

    public Door(DoorStatus theStatus){
        status = theStatus;
    }

    public DoorStatus getStatus(){
        return status;
    }

    public void setStatus(DoorStatus theStatus){
        status = theStatus;
    }

    public String toString(){
        return status.toString();
    }

    public static void main(String[] args){
        Door d = new Door();
    }


}
