package pota;

import it.kibo.fp.lib.Menu;
import pota.element.ElementsBalance;

public class Main {
    public static void main(String[] args) {
        ElementsBalance table = ElementsBalance.newRandomBalance(10,12);

        Player player1 = new Player("Giocatore 1");
        Player player2 = new Player("Giocatore 2");

        String[] entries = {"Nuova Partita", "Dichiara nomi giocatori"};
        Menu tamaMenu = new Menu("TamaGolem", entries, true, true, true);
        Menu.clearConsole();

        Battle battle = new Battle(player1, player2, 5, 10);
        do {
            switch(tamaMenu.choose()) {
                case 1 -> newGame(player1, player2);
                case 2 -> setPlayersNames(player1, player2);
                case 0 -> { return; }
            }
            Menu.clearConsole();
        } while(true);
    }

    public static void newGame(Player player1, Player player2) {
        Battle battle = new Battle(player1, player2, 5, 10);
        battle.start();
    }

    public static void setPlayersNames(Player player1, Player player2) {
        Menu.clearConsole();

        do {
            String[] entries = {String.format("Modifica nome: %s", player1.getName()), String.format("Modifica nome: %s", player2.getName())};
            Menu playerNamesMenu = new Menu("Modifica nomi giocatori", entries, true, true, false);

            switch(playerNamesMenu.choose()) {
                case 1 -> TamaMenu.setPlayerName(player1);
                case 2 -> TamaMenu.setPlayerName(player2);
                case 0 -> { return; }
            }
            Menu.clearConsole();
        } while(true);
    }

    public static void stats() {}
}