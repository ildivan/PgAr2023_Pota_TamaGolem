package pota.element;

public enum Element {
    FIRE,
    WATER,
    EARTH,
    WIND,
    MAGIC,
    KRYPTONITE,
    LIGHT,
    DARKNESS,
    CHAOS,
    LIGHTNING;

    /* List of elements that can be used in combat.
    * Depending on the desired difficulty,
    * the player can choose the desired amount of elements up to a maximum of ten (i.e., all of them),
    * which will be selected by the order in which they are entered into the program.*/

    public static Element elementOfValue(int value){   /* Elements identified by their position value */
        for (Element e : Element.values()) {
            if(e.ordinal() == value) return e;
        }
        throw new IllegalArgumentException("Element does not exist");
    }

    public static Element elementOfName(String name) {     /* Elements identified by their name */
        for (Element e : Element.values()) {
            if(e.name().equals(name.toUpperCase().trim())) return e;
        }
        throw new IllegalArgumentException("Element does not exist");
    }
}