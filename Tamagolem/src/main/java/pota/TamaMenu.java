package pota;

import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

public class TamaMenu {
    public static void setPlayerName(Player player) {
        Menu.clearConsole();
        player.setName(InputData.readString(String.format("Il nome attuale e' %s, inserisci quello nuovo: ", player.getName()), false));
    }
}
