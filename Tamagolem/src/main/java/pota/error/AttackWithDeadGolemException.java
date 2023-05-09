package pota.error;

public class AttackWithDeadGolemException extends RuntimeException{
    public AttackWithDeadGolemException() {
        this("Tried attacking with a dead golem");
    }

    public AttackWithDeadGolemException(String message) {
        super(message);
    }

    /*Exception presented in the case of attempting to launch an attack
    * despite the Golem running out of life points
     */
}

