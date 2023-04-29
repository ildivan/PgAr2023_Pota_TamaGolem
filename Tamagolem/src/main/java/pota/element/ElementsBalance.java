package pota.element;

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
        for (int i = 0; i < (numberOfElements-1); i++) {
            int partialSumOfRow = 0;
            for (int j = 0; j < i; j++) {
                partialSumOfRow += damageTable[i][j];
            }

            for (int j = i+1; j < (numberOfElements-1); j++) {
                int remainingElementsInRow = numberOfElements - j - 1;

                damageTable[i][j] = generateRandomDamage(maxDamage,partialSumOfRow,remainingElementsInRow);
                damageTable[j][i] = - damageTable[i][j];
                partialSumOfRow += damageTable[i][j];
            }
            damageTable[i][numberOfElements-1] = -partialSumOfRow;
            damageTable[numberOfElements-1][i] = partialSumOfRow;
        }

        printTable(damageTable);
        return new ElementsBalance(damageTable);
    }

    private static int generateRandomDamage(int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        int lowerBound = getLowerBound(maxDamage, partialSumOfRow, remainingElementsInRow);
        int upperBound = getUpperBound(maxDamage, partialSumOfRow, remainingElementsInRow);
        return generateValidRandomNumber(lowerBound,upperBound);
    }

    private static int generateValidRandomNumber(int lowerBound, int upperBound) {
        int randomNumber;
        do{
            randomNumber = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        }while(randomNumber == 0);
        return randomNumber;
    }

    private static int getLowerBound(int maxDamage, int partialSum, int remainingElements) {
        return Math.max(-maxDamage, -(partialSum + remainingElements * maxDamage));
    }

    private static int getUpperBound(int maxDamage, int partialSum, int remainingElements) {
        return Math.min(maxDamage, -(partialSum - remainingElements * maxDamage));
    }

    public int getDamage(Element first, Element second){
        return elementsEffects[first.ordinal()][second.ordinal()];
    }

    public static void printTable(int[][]mat){
        System.out.println(Arrays.deepToString(mat).replaceAll("], \\[","]\n["));
    }
}