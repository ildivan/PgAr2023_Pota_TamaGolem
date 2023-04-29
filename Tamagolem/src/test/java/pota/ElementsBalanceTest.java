package pota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pota.element.Element;
import pota.element.ElementsBalance;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementsBalanceTest {

    public static int numberOfElements;
    public static int maxDamage;
    public static ElementsBalance balance;

    @BeforeAll
    public static void setup() {
        numberOfElements = 10;
        maxDamage = 5;
        balance = ElementsBalance.newRandomBalance(numberOfElements,maxDamage);
    }

    @Test
    public void rowsShouldSumToZero() {
        for (int i = 0; i < numberOfElements; i++) {
            int sumOfRow = 0;
            for (int j = 0; j < numberOfElements; j++) {
                Element firstElement = Element.elementOfValue(i);
                Element secondElement = Element.elementOfValue(j);
                sumOfRow += balance.getDamage(firstElement,secondElement);
            }
            Assertions.assertEquals(0,sumOfRow);
        }
    }

    @Test
    public void columnsShouldSumToZero() {
        for (int j = 0; j < numberOfElements; j++) {
            int sumOfColumn = 0;
            for (int i = 0; i < numberOfElements; i++) {
                Element firstElement = Element.elementOfValue(i);
                Element secondElement = Element.elementOfValue(j);
                sumOfColumn += balance.getDamage(firstElement,secondElement);
            }
            Assertions.assertEquals(0,sumOfColumn);
        }
    }

    @Test
    public void shouldGenerateInBounds() {
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
