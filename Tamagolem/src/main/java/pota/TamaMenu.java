package pota;

import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;


import com.github.lalyos.jfiglet.FigletFont;

public class TamaMenu {
    private static final String SEPARATOR = "-----------------------------------------------------";

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
        welcome();
        String[] entries = {"New match", "Set player names"};
        Menu tamaMenu = new Menu("Tama Menu", entries, true, true, false);
        return tamaMenu.choose();
    }

    public static int setPlayersNamesMenu(Player player1, Player player2) {
        Menu.clearConsole();
        String[] entries = {String.format("Edit name: %s", player1.getName()), String.format("Edit name: %s", player2.getName())};
        Menu playerNamesMenu = new Menu("Edit players' name", entries, true, true, false);
        return playerNamesMenu.choose();
    }

    public static void pressEnterToContinue() {
		System.out.print("\n\nPress Enter to continue...");
		try {
			System.in.read();
		} catch (Exception e) {}
	}

    public static void pauseProgram() {
        try {
            Menu.wait(2000);
        } catch (InterruptedException e1) {}
    }

    public static void printWinner(Player player) {
        try {
            String playerName = new String(player.getName());
            playerName = playerName.substring(5, playerName.length()-4);
            System.out.println(FigletFont.convertOneLine(String.format("The winner is  %s", playerName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printSeparator() {
        System.out.println(SEPARATOR);
    }

    public static void welcome() {
        try {
            System.out.println(FigletFont.convertOneLine("Tamagolem"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
