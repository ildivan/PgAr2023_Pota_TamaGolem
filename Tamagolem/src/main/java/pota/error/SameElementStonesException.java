package pota.error;

public class SameElementStonesException extends RuntimeException{
    public SameElementStonesException() {
        this("Cannot summon a golem with the exact same element stones as the other!");
    }

    public SameElementStonesException(String message) {
        super(message);
    }
}

/* in case the same element is used simultaneously by the two players,
* the following exception is used thanks to which no health points are subtracted from either Golem.
 */