package pota;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.PrettyStrings;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        // main menu
        do {
            switch (TamaMenu.mainMenu()) {
                case 1 -> newGame(player1, player2);
                case 2 -> setPlayersNames(player1, player2);
                case 0 -> {
                    return;
                }
            }
        } while (true);
    }

    // Starts a new game, the number of elements will be prompted.
    public static void newGame(Player player1, Player player2) {
        Menu.clearConsole();
        System.out.println(PrettyStrings.frame("Battle", 60, true, false));

        int numberOfElements = getNumberOfElements();// numberOfElements must be between 3 and 10
        Battle battle = new Battle(player1, player2, numberOfElements, 100);
        battle.start();
        TamaMenu.pressEnterToContinue();
    }

    // Gets a number of elements within bounds from the user.
    private static int getNumberOfElements() {
        int numberOfElements = InputData.readInteger("How many elements do you want to evoke? [min 3, max 10]: ");
        while (numberOfElements > 10 | numberOfElements < 3) {
            System.out.println("You can summon a minimum of 3 and a maximum of 10 items!");
            TamaMenu.pauseTwoSeconds();
            Menu.clearConsole();
            numberOfElements = InputData.readInteger("How many elements do you want to evoke? [min 3, max 10]: ");
        }
        return numberOfElements;
    }

    // Menu used to change the names of the players.
    public static void setPlayersNames(Player player1, Player player2) {
        do {
            switch (TamaMenu.setPlayersNamesMenu(player1, player2)) {
                case 1 -> TamaMenu.setPlayerName(player1);
                case 2 -> TamaMenu.setPlayerName(player2);
                case 0 -> {
                    return;
                }
            }
        } while (true);
    }
}