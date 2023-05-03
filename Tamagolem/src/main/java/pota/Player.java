package pota;

import pota.error.NoMoreGolemsException;

public class Player {
    private int remainingGolems;
    private final int golemHealthPoints;

    private TamaGolem currentGolem;

    public Player(int numberOfGolems, int golemHealthPoints) {
        this.remainingGolems = numberOfGolems;
        this.golemHealthPoints = golemHealthPoints;
    }

    public TamaGolem getCurrentGolem() {
        return currentGolem;
    }

    public void nextGolem() {
        if (remainingGolems > 0) {
            remainingGolems--;
            currentGolem = new TamaGolem(golemHealthPoints);
        }
        throw new NoMoreGolemsException();
    }
}
