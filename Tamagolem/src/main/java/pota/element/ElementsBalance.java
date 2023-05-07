package pota.element;

public class ElementsBalance {
    private final int[][] elementsEffects;

    public ElementsBalance(int[][] elementsEffects){
        this.elementsEffects = new int[elementsEffects.length][];
        for (int i = 0; i < elementsEffects.length; i++) {
            this.elementsEffects[i] = elementsEffects[i].clone();
        }
    }

    public static ElementsBalance newRandomBalance(int numberOfElements, int maxDamage){
        boolean oddNumberOfElements = numberOfElements % 2 != 0;
        int[][] damageTable = new int[numberOfElements][numberOfElements];
        int[] rowsPartialSum = new int[numberOfElements];

        for (int i = 0; i < (numberOfElements / 2); i++) {
            for (int j = i + 1; j < (numberOfElements / 2); j++) {
                int remaining = (numberOfElements / 2) - j;
                if(oddNumberOfElements){
                    damageTable[i][j] = generateRandomDamage(maxDamage, rowsPartialSum[i], remaining);
                }else{
                    damageTable[i][j] = generateRandomDamage(maxDamage/2, rowsPartialSum[i], remaining);
                }
                damageTable[i][numberOfElements - j - 1] = damageTable[i][j];
                rowsPartialSum[i] += damageTable[i][j];
            }
            if(oddNumberOfElements){
                damageTable[i][numberOfElements / 2] = -rowsPartialSum[i];
                damageTable[i][numberOfElements - i - 1] = -rowsPartialSum[i];
            }else{
                damageTable[i][numberOfElements - i - 1] = -2*rowsPartialSum[i];
            }
        }
        if (oddNumberOfElements) {
            damageTable[numberOfElements / 2 - 1][numberOfElements / 2]
                    = generateRandomDamage(maxDamage, 0, 1);
            damageTable[numberOfElements / 2 - 1][numberOfElements / 2 + 1]
                    = -damageTable[numberOfElements / 2 - 1][numberOfElements / 2];
        } else {
            damageTable[numberOfElements / 2 - 2][numberOfElements / 2]
                    = generateRandomDamage(maxDamage / 2, damageTable[numberOfElements / 2 - 2][numberOfElements / 2 - 1], 1);
            damageTable[numberOfElements / 2 - 1][numberOfElements / 2]
                    = damageTable[numberOfElements / 2 - 2][numberOfElements / 2 - 1] - damageTable[numberOfElements / 2 - 2][numberOfElements / 2];
            damageTable[numberOfElements / 2 - 2][numberOfElements / 2 + 1]
                    = -(damageTable[numberOfElements / 2 - 2][numberOfElements / 2 - 1]
                    + damageTable[numberOfElements / 2 - 2][numberOfElements / 2]);
        }

        symmetrizeUpperTriangle(damageTable);
        toAntiSymmetricMatrix(damageTable);

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

    //Generates a number (that belongs to a sequence that should sum to zero)
    // between bounds that will allow the last element,
    // that will be equal to -sumOfSequence, to be between -maxDamage and maxDamage.
    private static int generateRandomDamage
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        int lowerBound = getLowerBoundNumberOfElements(maxDamage, partialSumOfRow ,remainingElementsInRow);
        int upperBound = getUpperBoundNumberOfElements(maxDamage, partialSumOfRow, remainingElementsInRow);
        if(remainingElementsInRow == 1){
            return generateValidRandomNumber(lowerBound,upperBound,-partialSumOfRow);
        }
        return generateValidRandomNumber(lowerBound,upperBound);
    }

    //Returns the minimum safe number to generate a number valid to generateRandomDamage requirements.
    private static int getLowerBoundNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.max(-maxDamage, -partialSumOfRow - remainingElementsInRow * maxDamage);
    }

    //Returns the minimum safe number to generate a number valid to generateRandomDamage requirements.
    private static int getUpperBoundNumberOfElements
            (int maxDamage, int partialSumOfRow, int remainingElementsInRow) {
        return Math.min(maxDamage, -partialSumOfRow + remainingElementsInRow * maxDamage);
    }

    //Generates a random number between the specified bounds that is not equal to zero or any given invalid values.
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

    /**
     * Gets the damage that the first element does to the second.
     * Can be negative if the second element is the strong element or zero if the elements are equal
     */
    public int getDamage(Element first, Element second){
        return elementsEffects[first.ordinal()][second.ordinal()];
    }
}