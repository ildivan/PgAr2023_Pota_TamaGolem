package pota;

import pota.element.ElementsBalance;

public class Main {
    public static void main(String[] args) {
        ElementsBalance table = ElementsBalance.newRandomBalance(10,12);

        Player player1 = new Player("Giocatore 1");
        Player player2 = new Player("Giocatore 2");

        do {
            switch(TamaMenu.mainMenu()) {
                case 1 -> newGame(player1, player2);
                case 2 -> setPlayersNames(player1, player2);
                case 3 -> TamaMenu.printWinner(player2);
                case 0 -> { return; }
            }
        } while(true);
    }

    public static void newGame(Player player1, Player player2) {
        Battle battle = new Battle(player1, player2, 10, 40);
        battle.start();
    }

    public static void setPlayersNames(Player player1, Player player2) {
        do {
            switch(TamaMenu.setPlayersNamesMenu(player1, player2)) {
                case 1 -> TamaMenu.setPlayerName(player1);
                case 2 -> TamaMenu.setPlayerName(player2);
                case 0 -> { return; }
            }
        } while(true);
    }
}