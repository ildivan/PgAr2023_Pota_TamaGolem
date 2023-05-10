package pota;

import it.kibo.fp.lib.AnsiColors;

/**
 * Stores the messages used in other parts of the project
 */
public class Literals {
    public static final String DEAD_GOLEM_MESSAGE_MESSAGE = "The TamaGolem of %s is dead\n";
    public static final String DAMAGE_TABLE = "\n\nDAMAGE TABLE: ";
    public static final String GOLEM_HEALTH_MESSAGE = "TamaGolem of %s: %d/%d\n";
    public static final String REACTING_STONES_MESSAGE = "%s and %s are reacting!\n";
    public static final String ATTACK_DAMAGE_MESSAGE = "The TamaGolem of %s does damage %d to the TamaGolem of %s\n";
    public static final String SAME_STONES_ERROR = AnsiColors.RED + "The two Golems cannot have the same stones!" + AnsiColors.RESET;
    public static final String YOU_HAVE_SELECTED = "You selected: ";
    public static final String ELEMENT_CONFIRMATION = "\n\nDo you confirm the elements?";
    public static final String REMAINING_ELEMENT_IN_STORAGE_MESSAGE = "- %s (%d / %d)\n";
    public static final String BATTLE_MESSAGE = "Battle: stones selection";
    public static final String SELECT_ELEMENTS_MESSAGE = "%s, take %d element stones from the remaining ones to summon the next golem:\n";
    public static final String ELEMENT_STONE_OUT_OF_STORAGE_MESSAGE = "The %s type stones are finished!\n";
    public static final String INSERT_ELEMENT_NAME = "\n\nEnter the element name: ";
}
