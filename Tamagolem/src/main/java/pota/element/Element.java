package pota.element;

import it.kibo.fp.lib.AnsiColors;

/**
 * Enum for the existing elements
 */
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
    LIGHTNING(AnsiColors.YELLOW_BACKGROUND + "LIGHTNING" + AnsiColors.RESET);

    private static final String ELEMENT_DOES_NOT_EXISTS = AnsiColors.RED + "Element does not exist" + AnsiColors.RESET;
    private final String element; // Used to make elements name colored.

    Element(String element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return element;
    }

    /**
     * Gets the element given its value in the enum
     * 
     * @param value the value of the element (e.g: 0 -> Element.FIRE)
     * @return The element corresponding to the value
     */
    public static Element elementOfValue(int value) { /* Elements identified by its position value */
        for (Element e : Element.values()) {
            if (e.ordinal() == value)
                return e;
        }
        throw new IllegalArgumentException(ELEMENT_DOES_NOT_EXISTS);
    }

    /**
     * Gets the element given its string name
     * 
     * @param name the name of the element (i.e: "fIrE" -> Element.FIRE)
     * @return The element corresponding to the string
     */
    public static Element elementOfName(String name) { /* Elements identified by its name */
        for (Element e : Element.values()) {
            if (e.name().equals(name.toUpperCase().trim()))
                return e;
        }
        throw new IllegalArgumentException(ELEMENT_DOES_NOT_EXISTS);
    }
}