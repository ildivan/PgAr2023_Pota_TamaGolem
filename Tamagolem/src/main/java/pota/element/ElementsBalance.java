package pota.element;

/**
 * Class used to generate and store the balance between a given amount of elements.
 * It generates a random balance using a matrix that has the following requirements:
 * - Has to be antisymmetric.
 * - Values in the same row or column must sum to zero.
 * - The values must be in the range -maxDamage and maxDamage.
 */
public class ElementsBalance {
    private final int[][] elementsEffects;

    /**
     * Use intended for testing purposes and in {@link #newRandomBalance(int, int)}
     * @param elementsEffects the table with the damages.
     */
    public ElementsBalance(int[][] elementsEffects){
        this.elementsEffects = new int[elementsEffects.length][];
        for (int i = 0; i < elementsEffects.length; i++) {
            this.elementsEffects[i] = elementsEffects[i].clone();
        }
    }

    /**
     * Static method that returns a new instance of ElementsBalance with a random damage table inside.
     * @param numberOfElements The size of the damage table.
     * @param maxDamage Tells the algorithm to generate only values between -maxDamage and maxDamage.
     * @return Instance of ElementsBalance.
     */
    public static ElementsBalance newRandomBalance(int numberOfElements, int maxDamage){
        //The algorithms work differently depending on the parity of numberOfElements.
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
        //Adjusts the elements near the center of the matrix if the number of elements in odd.
        if (oddNumberOfElements) {
            damageTable[numberOfElements / 2 - 1][numberOfElements / 2]
                    = generateRandomDamage(maxDamage, 0, 1);
            damageTable[numberOfElements / 2 - 1][numberOfElements / 2 + 1]
                    = -damageTable[numberOfElements / 2 - 1][numberOfElements / 2];
        } else {//Adjusts the elements near the center of the matrix if the number of elements in even.
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

    //Makes the upper triangle of the matrix symmetric.
    private static void symmetrizeUpperTriangle(int[][] matrix) {
        int length = matrix.length;
        for (int i = 0; i <= length/2; i++) {
            for (int j = i+1; j < length-i; j++) {
                matrix[length-j-1][length-i-1] = matrix[i][j];
            }
        }
    }

    //Transforms the matrix from a triangular matrix to an antisymmetric matrix.
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