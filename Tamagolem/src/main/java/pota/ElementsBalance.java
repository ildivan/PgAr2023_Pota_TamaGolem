package pota;

import java.util.Arrays;

public class ElementsBalance {
    private final int[][] elementsEffects;

    public ElementsBalance(int[][] elementsEffects){
        this.elementsEffects = new int[elementsEffects.length][];
        for (int i = 0; i < elementsEffects.length; i++) {
            this.elementsEffects[i] = elementsEffects[i].clone();
        }
    }

    public static ElementsBalance newRandomBalance(int numberOfElements, int maxDamage){

        int[][] damageTable = new int[numberOfElements][numberOfElements];

        int partialSumOfLastColumn = 0;
        for (int i = 0; i < (numberOfElements-1); i++) {
            int partialSumOfRow = 0;
            for (int j = 0; j < i; j++) {
                partialSumOfRow += damageTable[i][j];
            }
            int remainingColumns = numberOfElements - i - 1;
            for (int j = i+1; j < (numberOfElements-1); j++) {
                int remainingElements = numberOfElements - j - 1;
                damageTable[i][j] =
                        generateRandomDamage(
                            maxDamage,
                            partialSumOfRow,
                            remainingElements);
                damageTable[j][i] = -damageTable[i][j];
                partialSumOfRow += damageTable[i][j];
            }
            partialSumOfLastColumn += -partialSumOfRow;
            damageTable[i][numberOfElements-1] = -partialSumOfRow;
            damageTable[numberOfElements-1][i] = partialSumOfRow;
        }
        printTable(damageTable);
        return new ElementsBalance(damageTable);
    }

    private static int generateRandomDamage(int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        int lowerBound = getLowerBound(maxDamage,partialSumOfRow,remainingElementsInRow);
        int upperBound = getUpperBound(maxDamage,partialSumOfRow,remainingElementsInRow);
        return generateValidRandomNumber(lowerBound, upperBound, partialSumOfRow);
    }

    private static int generateValidRandomNumber(int lowerBound, int upperBound, int partialSum) {
        int randomNumber;
        do{
            randomNumber = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        }while(randomNumber == 0 || randomNumber == partialSum);
        return randomNumber;
    }

    private static int getLowerBound(int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.max(-maxDamage, -(partialSumOfRow + remainingElementsInRow * maxDamage));
    }

    private static int getUpperBound(int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.min(maxDamage, -(partialSumOfRow - remainingElementsInRow * maxDamage));
    }

    public int getDamage(Element first, Element second){
        return elementsEffects[first.ordinal()][second.ordinal()];
    }

    public static void printTable(int[][]mat){
        System.out.println(Arrays.deepToString(mat).replaceAll("], \\[","]\n["));
    }
}