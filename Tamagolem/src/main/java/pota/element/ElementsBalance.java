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
        int[] columnsPartialSum = new int[numberOfElements];

        for (int i = 0; i < (numberOfElements-1); i++) {
            int partialSumOfRow = 0;
            for (int j = 0; j < i; j++) {
                partialSumOfRow += damageTable[i][j];
            }

            for (int j = i+1; j < (numberOfElements-1); j++) {
                int remainingElementsInRow = numberOfElements - j - 1;

                damageTable[i][j]
                        = generateRandomDamage(maxDamage,partialSumOfRow,columnsPartialSum[j],remainingElementsInRow);
                damageTable[j][i] = - damageTable[i][j];


                partialSumOfRow += damageTable[i][j];
                columnsPartialSum[j] += damageTable[i][j];


                printTable(damageTable);
            }
            damageTable[i][numberOfElements-1] = -partialSumOfRow;
            damageTable[numberOfElements-1][i] = partialSumOfRow;
        }
        return new ElementsBalance(damageTable);
    }

    private static int generateRandomDamage
            (int maxDamage, int partialSumOfRow,int partialSumOfColumn,int remainingElementsInRow) {
        int lowerBound = getLowerBound(maxDamage, partialSumOfRow, partialSumOfColumn,remainingElementsInRow);
        int upperBound = getUpperBound(maxDamage, partialSumOfRow, partialSumOfColumn,remainingElementsInRow);
        return generateValidRandomNumber(lowerBound,upperBound);
    }

    private static int generateValidRandomNumber(int lowerBound, int upperBound) {
        int randomNumber;
        do{
            randomNumber = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
        }while(randomNumber == 0);
        return randomNumber;
    }

    private static int getLowerBound(int maxDamage, int partialSumOfRow, int partialSumOfColumn, int remainingElementsInRow) {
        return Math.max(
                Math.max(-maxDamage, -(partialSumOfRow + remainingElementsInRow * maxDamage)),
                -(maxDamage * remainingElementsInRow) - partialSumOfColumn);
    }

    private static int getUpperBound(int maxDamage, int partialSumOfRow, int partialSumOfColumn, int remainingElementsInRow) {
        return Math.min(
                Math.min(maxDamage, -partialSumOfRow + remainingElementsInRow * maxDamage),
                (maxDamage*remainingElementsInRow) - partialSumOfColumn);
    }

    public int getDamage(Element first, Element second){
        return elementsEffects[first.ordinal()][second.ordinal()];
    }

    public static void printTable(int[][]mat){
        System.out.println(Arrays.deepToString(mat).replaceAll("], \\[","]\n["));
    }
}