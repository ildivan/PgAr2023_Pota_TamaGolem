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
        int[] rowsPartialSum = new int[numberOfElements];

        if(numberOfElements %2 != 0) {
            for (int i = 0; i < (numberOfElements/2); i++) {
                for (int j = i+1; j < (numberOfElements/2); j++) {
                    int remaining = (numberOfElements/2) - j;
                    damageTable[i][j] = generateRandomDamageForOddNumberOfElements(maxDamage, rowsPartialSum[i], remaining);
                    damageTable[i][numberOfElements-j-1] = damageTable[i][j];
                    rowsPartialSum[i] += damageTable[i][j];
                }
                damageTable[i][numberOfElements/2] = -rowsPartialSum[i];
                damageTable[i][numberOfElements-i-1] = -rowsPartialSum[i];
            }
            damageTable[numberOfElements/2-1][numberOfElements/2]
                    = generateRandomDamageForOddNumberOfElements(maxDamage,0,1);
            damageTable[numberOfElements/2-1][numberOfElements/2 + 1]
                    = -damageTable[numberOfElements/2-1][numberOfElements/2];
        }else{
            for (int i = 0; i < (numberOfElements/2); i++) {
                for (int j = i+1; j < (numberOfElements/2); j++) {
                    int remaining = (numberOfElements/2) - j;
                    damageTable[i][j] = generateRandomDamageForEvenNumberOfElements(maxDamage, rowsPartialSum[i], remaining);
                    damageTable[i][numberOfElements-j-1] = damageTable[i][j];
                    rowsPartialSum[i] += damageTable[i][j];
                }
                damageTable[i][numberOfElements-i-1] = -2*rowsPartialSum[i];
            }

            damageTable[numberOfElements/2 -2][numberOfElements/2] /= 2;
            damageTable[numberOfElements/2 - 1][numberOfElements/2]
                    = damageTable[numberOfElements/2 -2][numberOfElements/2];
            damageTable[numberOfElements/2 -2][numberOfElements/2 + 1]
                    = -(damageTable[numberOfElements/2 -1][numberOfElements/2-1]
                        + damageTable[numberOfElements/2 -1][numberOfElements/2]);
        }

        symmetrizeUpperTriangle(damageTable);
        toAntiSymmetricMatrix(damageTable);

        printTable(damageTable);
        return new ElementsBalance(damageTable);
    }

    private static void symmetrizeUpperTriangle(int[][] matrix) {
        int length = matrix.length;
        for (int i = 0; i <= length/2; i++) {
            for (int j = i+1; j < length-i; j++) {
                matrix[length-j-1][length-i-1] = matrix[i][j];
            }
        }
    }

    private static void toAntiSymmetricMatrix(int[][] matrix) {
        int  length = matrix.length;
        for (int i = 0; i < length-1; i++) {
            for (int j = i+1; j < length; j++) {
                matrix[j][i] = -matrix[i][j];
            }
        }
    }

    private static int generateRandomDamageForOddNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        int lowerBound = getLowerBoundForOddNumberOfElements(maxDamage, partialSumOfRow ,remainingElementsInRow);
        int upperBound = getUpperBoundForOddNumberOfElements(maxDamage, partialSumOfRow, remainingElementsInRow);
        if(remainingElementsInRow == 1){
            return generateValidRandomNumber(lowerBound,upperBound,-partialSumOfRow);
        }
        return generateValidRandomNumber(lowerBound,upperBound);
    }

    private static int getLowerBoundForOddNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.max(-maxDamage, -partialSumOfRow - remainingElementsInRow * maxDamage);
    }

    private static int getUpperBoundForOddNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.min(maxDamage, -partialSumOfRow + remainingElementsInRow * maxDamage);
    }

    private static int generateRandomDamageForEvenNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        int lowerBound = getLowerBoundForEvenNumberOfElements(maxDamage, partialSumOfRow ,remainingElementsInRow);
        int upperBound = getUpperBoundForEvenNumberOfElements(maxDamage, partialSumOfRow, remainingElementsInRow);
        if(remainingElementsInRow == 1){
            return generateValidRandomNumber(lowerBound,upperBound,-partialSumOfRow);
        }
        return generateValidRandomNumber(lowerBound,upperBound);
    }

    private static int getLowerBoundForEvenNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.max(-maxDamage,-(2*partialSumOfRow + 2*maxDamage*remainingElementsInRow -maxDamage)) ;
    }

    private static int getUpperBoundForEvenNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.min(maxDamage,-(2*partialSumOfRow - 2*maxDamage*remainingElementsInRow +maxDamage));
    }

    private static int generateValidRandomNumber(int lowerBound, int upperBound, int...invalidValues) {
        int randomNumber;
        boolean invalid;
        do{
            invalid = false;
            randomNumber = (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
            for (int v : invalidValues) {
                if (v == randomNumber) {
                    invalid = true;
                    break;
                }
            }
            if(randomNumber == 0) invalid = true;
        }while(invalid);
        return randomNumber;
    }

    public int getDamage(Element first, Element second){
        return elementsEffects[first.ordinal()][second.ordinal()];
    }

    public static void printTable(int[][]mat){
        System.out.println(Arrays.deepToString(mat).replaceAll("], \\[","]\n["));
    }
}