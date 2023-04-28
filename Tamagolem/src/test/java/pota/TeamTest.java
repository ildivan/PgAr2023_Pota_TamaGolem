package pota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pota.error.NoMoreGolemsException;


public class TeamTest {

    @Test
    public void shouldLetMeGetGolemsTheRightAmountOfTimes(){
        int numOfGolems = 10;
        Team team = new Team(numOfGolems,10);
        for (int i = 0; i < numOfGolems; i++) {
            team.getNextGolem();
        }
        Assertions.assertThrows(NoMoreGolemsException.class, team::getNextGolem);
    }
}
