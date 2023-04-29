package pota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pota.element.Element;
import pota.element.ElementsBalance;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementsBalanceTest {

    @Test
    public void shouldGenerateInBounds() {
        int numberOfElements = 10;
        int maxDamage = 13;
        ElementsBalance balance = ElementsBalance.newRandomBalance(numberOfElements,maxDamage);
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                Element firstElement = Element.elementOfValue(i);
                Element secondElement = Element.elementOfValue(j);
                assertTrue(balance.getDamage(firstElement,secondElement) >= -maxDamage
                        && balance.getDamage(firstElement,secondElement) <= maxDamage);
            }
        }
    }

    @Test
    public void shouldRetrieveDamagesCorrectly() {
        int[][] balanceMat = new int[][]   {{0, 2, -2},
                                        {-2, 0, 2},
                                        {2, -2, 0}};
        ElementsBalance balance = new ElementsBalance(balanceMat);
        Assertions.assertEquals(2,balance.getDamage(Element.FIRE,Element.WATER));
        Assertions.assertEquals(-2,balance.getDamage(Element.WATER,Element.FIRE));
    }
}
