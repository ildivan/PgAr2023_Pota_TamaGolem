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

    public static Element elementOfValue(int value){   /*  */
        for (Element e : Element.values()) {
            if(e.ordinal() == value) return e;
        }
        throw new IllegalArgumentException("Element does not exist");
    }

    public static Element elementOfName(String name) {     /* identification of the element by name */
        for (Element e : Element.values()) {
            if(e.name().equals(name.toUpperCase().trim())) return e;
        }
        throw new IllegalArgumentException("Element does not exist");
    }

}