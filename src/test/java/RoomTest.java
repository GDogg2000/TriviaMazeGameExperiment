import org.junit.jupiter.api.Test;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest
{
    @Test
    void TestConstructor(){

        Room r = new Room();

        assertEquals(5, r.getX() );

    }
}
