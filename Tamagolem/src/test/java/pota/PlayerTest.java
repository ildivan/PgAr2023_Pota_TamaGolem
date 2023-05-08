package pota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pota.error.NoMoreGolemsException;


public class PlayerTest {

    @Test
    public void shouldLetMeGetGolemsTheRightAmountOfTimes(){
        int numOfGolems = 10;
        int golemHealthPoints = 10;
        Player player = new Player("Giocatore 1");
        player.createNewTeam(numOfGolems, golemHealthPoints);
        for (int i = 0; i < numOfGolems; i++) {
            player.getTeam().nextGolem();
        }
        Assertions.assertThrows(NoMoreGolemsException.class, player.getTeam()::nextGolem);
    }
}
