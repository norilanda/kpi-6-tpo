package IO;

import Matrix.IMatrix;

public class ConsoleMatrixPrinter implements IMatrixPrinter{
    public void PrintMatrix(IMatrix matrix) {
        var innerMatrix = matrix.getMatrix();
        for (int i = 0; i < matrix.getRowNumber(); i++) {
            for (int j = 0; j < matrix.getColumnNumber(); j++) {
                System.out.print(innerMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
