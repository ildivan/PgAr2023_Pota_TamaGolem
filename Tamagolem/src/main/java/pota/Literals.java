package pota;

import it.kibo.fp.lib.AnsiColors;

/**
 * Stores the messages used in other parts of the project
 */
public class Literals {
    public static final String DEAD_GOLEM_MESSAGE_MESSAGE = "Il golem di %s è morto!\n";
    public static final String DAMAGE_TABLE = "\n\nTABELLA DEI DANNI: ";
    public static final String GOLEMS_REMAINING_HEALTH = "Salute rimanente dei golem: ";
    public static final String GOLEM_HEALTH_MESSAGE = "Golem di %s: %d/%d\n";
    public static final String REACTING_STONES_MESSAGE = "Le pietre %s e %s stanno reagendo!\n";
    public static final String ATTACK_DAMAGE_MESSAGE = "Il golem di %s fa danno %d al golem di %s\n";
    public static final String SAME_STONES_ERROR = AnsiColors.RED + "I due Golem non possono avere le stesse pietre!" + AnsiColors.RESET;
    public static final String YOU_HAVE_SELECTED = "Hai selezionato: ";
    public static final String ELEMENT_CONFIRMATION = "\n\nConfermi gli elementi";
    public static final String REMAINING_ELEMENT_IN_STORAGE_MESSAGE = "- %s (%d / %d)\n";
    public static final String BATTLE_MESSAGE = "Battaglia: selezione pietre";
    public static final String SELECT_ELEMENTS_MESSAGE = "%s, prendi %d pietre degli elementi dalle rimanenti per evocare il prossimo golem:\n";
    public static final String ELEMENT_STONE_OUT_OF_STORAGE_MESSAGE = "Le pietre di tipo %s sono finite!\n";
    public static final String INSERT_ELEMENT_NAME = "\n\nInserisci il nome dell' elemento: ";
    public static final String MODIFICA_NOME = "Modifica nome: %s";
    public static final String PRESS_ENTER_TO_CONTINUE = "\n\nPremi Invio per continuare...";
    public static final String CURRENT_PLAYER_NAME = "Il nome attuale e' %s, inserisci quello nuovo: ";
    public static final String SEPARATOR = "-----------------------------------------------------";
}
