package pota;

import pota.error.NoMoreGolemsException;

public class Team {
    private TamaGolem currentGolem;
    private int remainingGolems;
    private final int golemHealthPoints;
    
    public Team(int numberOfGolems, int golemHealthPoints) {
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
            return;
        }
        throw new NoMoreGolemsException();
    }
}
