package Matrix;

public class MatrixHelper {
    public static int[] copyColumn(int[][] matrix, int colIndex) {
        int size = matrix.length;

        var col = new int [size];
        for (int j = 0; j < size; j++) {
            col[j] = matrix[j][colIndex];
        }
        return col;
    }

    public static int calculateMatrixElement(int[] rowA, int[] columnB) {
        var calculatedElement = 0;
        for (int k = 0; k < rowA.length; k++) {
            calculatedElement += rowA[k] * columnB[k];
        }

        return calculatedElement;
    }
    public static void addBlockToMatrix(int[][] block, int[][] matrix, int rowStart, int columnStart) {
        int blockSize = block.length;

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                matrix[rowStart + i][columnStart + j] += block[i][j];
            }
        }
    }

    public static int[][] multiplyBlocks(int[][] blockA, int[][] blockB){
        var blockSize = blockA.length;
        var calculatedBlock = new int[blockSize][blockSize];

        for (int i = 0; i < blockSize; i++) {
            calculatedBlock[i] = new int[blockSize];

            for (int j = 0; j < blockSize; j++) {
                for (int k = 0; k < blockSize; k++) {
                    calculatedBlock[i][j] += blockA[i][k] * blockB[k][j];
                }
            }
        }

        return calculatedBlock;
    }

    public static int [][][] spitMatrixToFragments(int[][] matrix, int fragmentsPerRow) {
        var size = matrix.length;

        int fragmentSize = size / fragmentsPerRow;
        int fragmentsNumber = fragmentsPerRow * fragmentsPerRow;

        int[][][] fragments = new int[fragmentsNumber][fragmentSize][fragmentSize];

        int fragmentColStartIndex = 0;
        int fragmentIndex = 0;

        while (fragmentColStartIndex < size) {
            for (int j = 0; j < fragmentsPerRow; j++) {
                var newFragment = new int[fragmentSize][fragmentSize];

                for (int k = 0; k < fragmentSize; k++) {
                    for (int l = 0; l < fragmentSize; l++) {
                        newFragment[k][l] = matrix[fragmentColStartIndex + k][j* fragmentSize + l];
                    }
                }
                fragments[fragmentIndex] = newFragment;
                fragmentIndex++;
            }
            fragmentColStartIndex += fragmentSize;
        }

        return fragments;
    }
}
