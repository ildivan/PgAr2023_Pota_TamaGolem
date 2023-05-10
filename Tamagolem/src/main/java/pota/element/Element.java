package pota.element;

import it.kibo.fp.lib.AnsiColors;

public enum Element {
    FIRE(AnsiColors.RED + "FIRE" + AnsiColors.RESET),
    WATER(AnsiColors.BLUE + "WATER" + AnsiColors.RESET),
    EARTH(AnsiColors.GREEN + "EARTH" + AnsiColors.RESET),
    WIND(AnsiColors.CYAN + "WIND" + AnsiColors.RESET),
    MAGIC(AnsiColors.PURPLE + "MAGIC" + AnsiColors.RESET),
    KRYPTONITE(AnsiColors.GREEN_BACKGROUND + "KRYPTONITE" + AnsiColors.RESET),
    LIGHT(AnsiColors.YELLOW + "LIGHT" + AnsiColors.RESET),
    DARKNESS(AnsiColors.PURPLE_BACKGROUND + "DARKNESS" + AnsiColors.RESET),
    CHAOS(AnsiColors.CYAN_BACKGROUND + "CHAOS" + AnsiColors.RESET),
    LIGHTNING(AnsiColors.YELLOW_BACKGROUND + "LIGHTNING"+AnsiColors.RESET);

    /* List of elements that can be used in combat.
    * Depending on the desired difficulty,
    * the player can choose the desired amount of elements up to a maximum of ten (i.e., all of them),
    * which will be selected by the order in which they are entered into the program.*/

    private static final String ELEMENT_NOT_EXISTS = AnsiColors.RED + "Element does not exist" + AnsiColors.RESET;
    private String element;

    private Element(String element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return element;
    }

    public static Element elementOfValue(int value){   /* Elements identified by their position value */
        for (Element e : Element.values()) {
            if(e.ordinal() == value) return e;
        }
        throw new IllegalArgumentException(ELEMENT_NOT_EXISTS);
    }

    public static Element elementOfName(String name) {     /* Elements identified by their name */
        for (Element e : Element.values()) {
            if(e.name().equals(name.toUpperCase().trim())) return e;
        }
        throw new IllegalArgumentException(ELEMENT_NOT_EXISTS);
    }
}