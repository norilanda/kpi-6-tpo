package Algorithms;

import Matrix.IMatrix;
import Matrix.Matrix;
import Matrix.MatrixHelper;

public class FoxMatrixMultiplication implements IMatrixMultiplication{

    private final int rowFragmentsNumber;
    public FoxMatrixMultiplication(int rowFragmentsNumber) {
        this.rowFragmentsNumber = rowFragmentsNumber;
    }
    @Override
    public IMatrix Multiply(IMatrix A, IMatrix B) {
        int[][] matrixC = new int[A.getRowNumber()][A.getColumnNumber()];

        var matrixA = A.getMatrix();
        var matrixB = B.getMatrix();

        var size = A.getRowNumber();

        var blocksA = MatrixHelper.spitMatrixToFragments(matrixA, rowFragmentsNumber);
        var blocksB = MatrixHelper.spitMatrixToFragments(matrixB, rowFragmentsNumber);

        int fragmentSize = size / rowFragmentsNumber;

        for (int iteration = 0; iteration < rowFragmentsNumber; iteration++) {
            for (int i = 0; i < rowFragmentsNumber; i++) {
                var index = (i + iteration) % rowFragmentsNumber;
//                System.out.print("process (row) " + i + ": ");

                for (int j = 0; j < rowFragmentsNumber; j++) {
                    var blockAIndex = (i * rowFragmentsNumber) + index;
                    var blockBIndex = (index * rowFragmentsNumber) + j;

//                    System.out.print(" " + blockAIndex + " " + blockBIndex + ",  ");

                    var blockC = MatrixHelper.multiplyBlocks(blocksA[blockAIndex], blocksB[blockBIndex]);
                    MatrixHelper.addBlockToMatrix(blockC, matrixC, i * fragmentSize, j * fragmentSize);
                }
            }
//            System.out.println();
        }

        return new Matrix(matrixC);
    }
}
