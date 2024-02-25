package Algorithms;

import Matrix.IMatrix;
import Matrix.Matrix;

public class ColumnMatrixMultiplication implements IMatrixMultiplication{
    public IMatrix Multiply(IMatrix A, IMatrix B) {
        int[][] matrixC = new int[A.getRowNumber()][A.getColumnNumber()];

        var matrixA = A.getMatrix();
        var matrixB = B.getMatrix();

        var size = A.getRowNumber();
        for (int iteration = 0; iteration < size; iteration++) {
//            System.out.print("iteration: " + iteration + ": ");

            for (int row = 0; row < size; row++) { // j = process = row
                var col = (row - iteration + size)%size;
//                System.out.print(" " + row + " " + col + ", ");
                var calculatedElement = 0;
                for (int k = 0; k < size; k++) {
                    calculatedElement += matrixA[row][k] * matrixB[k][col];
//                    System.out.print(" " + row + "," + k + " " + k + "," + col + "  ");
                }
                matrixC[row][col] = calculatedElement;
//                System.out.print(calculatedElement+",");
            }
//            System.out.println();
        }
        return new Matrix(matrixC);
    }
}

