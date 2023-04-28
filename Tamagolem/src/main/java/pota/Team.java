package pota;

import pota.error.NoMoreGolemsException;

public class Team {
    private int remainingGolems;
    private final int golemHealthPoints;

    public Team(int numberOfGolems,int golemHealthPoints) {
        this.remainingGolems = numberOfGolems;
        this.golemHealthPoints = golemHealthPoints;
    }

    public TamaGolem getNextGolem() {
        if (remainingGolems > 0) {
            remainingGolems--;
            return new TamaGolem(golemHealthPoints);
        }
        throw new NoMoreGolemsException();
    }
}
