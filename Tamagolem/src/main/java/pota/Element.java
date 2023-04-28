package pota;

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

    public static Element elementOfValue(int value){
        for (Element e : Element.values()){
            if(e.ordinal() == value) return e;
        }
        throw new IllegalArgumentException("Element does not exist");
    }
}