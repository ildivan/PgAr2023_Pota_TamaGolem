package pota;

import pota.element.Element;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class TamaGolem {
    private Deque<Element> elementStones;
    private int healthPoints;

    public TamaGolem(int healthPoints) {
        this.healthPoints = healthPoints;
        elementStones = new ArrayDeque<>();
    }

    public void setElementsStones(Element... stones) {
        elementStones.addAll(Arrays.asList(stones));
    }

    public Element getNextStone() {
        Element nextElement = elementStones.element();
        elementStones.add(nextElement);
        elementStones.remove();
        return nextElement;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void damageGolem(int amountOfHealthToRemove) {
        healthPoints -= amountOfHealthToRemove;
    }

    public boolean isAlive() {
        return healthPoints <= 0;
    }

}
