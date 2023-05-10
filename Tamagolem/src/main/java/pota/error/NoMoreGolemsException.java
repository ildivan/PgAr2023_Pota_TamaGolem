package pota.error;

/**
 * Exception thrown in case the player has run out of golems.
 */
public class NoMoreGolemsException extends RuntimeException{
    public NoMoreGolemsException() {
        this("No more TamaGolems.");
    }

    public NoMoreGolemsException(String message) {
        super(message);
    }
}


