package pota;

import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.InputData;

import com.github.lalyos.jfiglet.FigletFont;

/**
 * The <strong>TamaMenu</strong> class contains all the static methods
 * called by other classes that need to print contents in Menus.
 * It also contains other features, such as pausing the program until the return
 * key is pressed.
 */
public class TamaMenu {

    // Clears the console and ask user to insert the new Playe's name.
    public static void setPlayerName(Player player) {
        Menu.clearConsole();
        player.setName(InputData.readString(String.format(Literals.CURRENT_PLAYER_NAME, player.getName()), false));
    }

    // Prints the table containing the Element balance.
    public static void printElementBalance(String[][] balance) {
        final PrettyPrinter printer = new PrettyPrinter(System.out);
        printer.print(balance);
    }

    // Clears the console, prints the welcome writing and prints the main menu.
    public static int mainMenu() {
        Menu.clearConsole();
        welcome();
        String[] entries = { "Nuova Partita", "Imposta nomi giocatori" };
        Menu tamaMenu = new Menu("Tama Menu", entries, true, true, false);
        return tamaMenu.choose();
    }

    // Prints the menu for changing players' names.
    public static int setPlayersNamesMenu(Player player1, Player player2) {
        Menu.clearConsole();
        String[] entries = { String.format(Literals.MODIFICA_NOME, player1.getName()),
                String.format(Literals.MODIFICA_NOME, player2.getName()) };
        Menu playerNamesMenu = new Menu("Modifica nomi giocatori", entries, true, true, false);
        return playerNamesMenu.choose();
    }

    // Pauses the program until user press the return key.
    public static void pressEnterToContinue() {
        System.out.print(Literals.PRESS_ENTER_TO_CONTINUE);
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    // Pauses the program for 2 seconds.
    public static void pauseTwoSeconds() {
        try {
            Menu.wait(2000);
        } catch (InterruptedException e1) {
        }
    }

    // Prints the ASCII art of the winner's name.
    public static void printWinner(Player player) {
        try {
            String playerName = new String(player.getName());
            playerName = playerName.substring(5, playerName.length() - 4);
            System.out.println(FigletFont.convertOneLine(String.format("Vince  %s", playerName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prints a line separator.
    public static void printSeparator() {
        System.out.println(Literals.SEPARATOR);
    }

    // Prints the welcome ASCII art text.
    public static void welcome() {
        try {
            System.out.println(FigletFont.convertOneLine("Tamagolem"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
