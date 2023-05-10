package pota;

import pota.error.NoMoreGolemsException;

/**
 * Represents the TamaGolem instances a Player has during a Battle,
 * They are given to the Player sequentially as they are not all created in the beginning.
 */
public class Team {
    private TamaGolem currentGolem; //The current TamaGolem instance used in battle.
    private int remainingGolems; //How many golems are remaining after currentGolem dies.
    private final int golemHealthPoints; //The amount of health the golems are created with.

    /**
     * @param numberOfGolems the numbe of golems the player will be able to generate.
     * @param golemHealthPoints the health every golem will be assigned at creation.
     */
    public Team(int numberOfGolems, int golemHealthPoints) {
        this.remainingGolems = numberOfGolems;
        this.golemHealthPoints = golemHealthPoints;
    }

    /**
     * @return The current TamaGolem instance.
     */
    public TamaGolem getCurrentGolem() {
        return currentGolem;
    }

    /**
     * If possible, it is created a new golem to use in battle,
     * if not an exception is thrown.
     * @see NoMoreGolemsException
     */
    public void nextGolem() {
        if (remainingGolems > 0) {
            remainingGolems--;
            currentGolem = new TamaGolem(golemHealthPoints);
            return;
        }
        throw new NoMoreGolemsException();
    }
}
