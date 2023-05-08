package pota.error;

public class NoMoreGolemsException extends RuntimeException{
    public NoMoreGolemsException() {
        this("No more golems.");
    }

    public NoMoreGolemsException(String message) {
        super(message);
    }
}

