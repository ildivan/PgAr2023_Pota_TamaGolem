package pota.error;

public class SameElementStonesException extends RuntimeException{
    public SameElementStonesException() {
        this("Cannot summon a golem with the exact same element stones as the other!");
    }

    public SameElementStonesException(String message) {
        super(message);
    }
}
