package pota.error;

public class NoMoreGolemsException extends RuntimeException{
    public NoMoreGolemsException() {
        this("No more golems.");
    }

    public NoMoreGolemsException(String message) {
        super(message);
    }
}

// Exception presented in case the player has reached the maximum number of usable golems


