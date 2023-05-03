package pota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pota.error.NoMoreGolemsException;


public class PlayerTest {

    @Test
    public void shouldLetMeGetGolemsTheRightAmountOfTimes(){
        int numOfGolems = 10;
        Player team = new Player(numOfGolems,10);
        for (int i = 0; i < numOfGolems; i++) {
            team.getNextGolem();
        }
        Assertions.assertThrows(NoMoreGolemsException.class, team::getNextGolem);
    }
}
