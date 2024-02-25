package Matrix;

public class Matrix implements IMatrix {
    private final int rowNumber;
    private final int columnNumber;
    private final int[][] matrix;
    public Matrix(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        matrix = new int [rowNumber][columnNumber];
    }

    public Matrix(int[][] matrix) {
        rowNumber = matrix.length;
        columnNumber = matrix[0].length;
        this.matrix = matrix;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
