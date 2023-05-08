package pota.error;

public class AttackWithDeadGolemException extends RuntimeException{
    public AttackWithDeadGolemException() {
        this("Tried attacking with a dead golem");
    }

    public AttackWithDeadGolemException(String message) {
        super(message);
    }
}

