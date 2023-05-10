package pota;

import pota.element.Element;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Represents a Golem , it is used for battles by making their element stones
 * react.
 */
public class TamaGolem {
    private Deque<Element> elementStones; // Represents the stones that the golem retains.
    private int healthPoints; // Its current health

    /**
     * @param healthPoints its max health
     */
    public TamaGolem(int healthPoints) {
        this.healthPoints = healthPoints;
        elementStones = new ArrayDeque<>();
    }

    /**
     * Sets the element stones.
     * 
     * @param stones the stones to give to the golem
     */
    public void setElementsStones(Element... stones) {
        elementStones.addAll(Arrays.asList(stones));
    }

    /**
     * @return all the element stones inside the golem in the order they are at the
     *         moment.
     */
    public List<Element> getElementStones() {
        return elementStones.stream().toList();
    }

    /**
     * @return The next stone in the golem digestive system.
     */
    public Element getNextStone() {
        Element nextElement = elementStones.element();
        elementStones.add(nextElement); // The stone is put at the tail of the queue
        elementStones.remove(); // and removed from the head to make the system circular.
        return nextElement;
    }

    /**
     * @return the current health of the golem.
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Decreases the health of the golem by the specified amount.
     * 
     * @param amountOfHealthToRemove the amount of health to remove.
     */
    public void damageGolem(int amountOfHealthToRemove) {
        healthPoints -= amountOfHealthToRemove;
        if (healthPoints < 0)
            healthPoints = 0;
    }

    /**
     * @return If the golem is alive.
     */
    public boolean isAlive() {
        return healthPoints > 0;
    }

}