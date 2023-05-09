package pota;

import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

import com.github.lalyos.jfiglet.FigletFont;

public class TamaMenu {
    public static void setPlayerName(Player player) {
        Menu.clearConsole();
        player.setName(InputData.readString(String.format("Il nome attuale e' %s, inserisci quello nuovo: ", player.getName()), false));
    }

    public static void printElementBalance(String[][] balance) {
        final PrettyPrinter printer = new PrettyPrinter(System.out);
        printer.print(balance);
    }

    public static int mainMenu() {
        Menu.clearConsole();
        String[] entries = {"Nuova Partita", "Dichiara nomi giocatori", "Stampa vincitore"};
        Menu tamaMenu = new Menu("TamaGolem", entries, true, true, true);
        return tamaMenu.choose();
    }

    public static int setPlayersNamesMenu(Player player1, Player player2) {
        Menu.clearConsole();
        String[] entries = {String.format("Modifica nome: %s", player1.getName()), String.format("Modifica nome: %s", player2.getName())};
        Menu playerNamesMenu = new Menu("Modifica nomi giocatori", entries, true, true, false);
        return playerNamesMenu.choose();
    }

    public static void printWinner(Player player) {
        String asciiArt1 = "";
        try {
            asciiArt1 = FigletFont.convertOneLine(String.format("%s  Wins", player.getName()));
        } catch (Exception e) {}
        System.out.println(asciiArt1);
        pressEnterToContinue();
    }

    public static void pressEnterToContinue() {
		System.out.print("\n\nPremi Invio per continuare...");
		try {
			System.in.read();
		} catch (Exception e) {}
	}
}
