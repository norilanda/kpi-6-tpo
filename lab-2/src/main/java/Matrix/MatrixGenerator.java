package Matrix;

public class MatrixGenerator {
    public static IMatrix generateMatrix(int size, int fillNumber) {
        var matrix = new int [size] [size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = fillNumber;
            }
        }
        return new Matrix(matrix);
    }

    public static IMatrix generateZerosMatrixWithDiagonal(int size, int fillNumber) {
        var matrix = new int [size] [size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = i == j ? fillNumber : 0;
            }
        }
        return new Matrix(matrix);
    }

    public static IMatrix getSimpleMatrix(int size) {
        var matrix = new int [size] [size];

        int counter = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = counter;
                counter++;
            }
        }
        return new Matrix(matrix);
    }
}
