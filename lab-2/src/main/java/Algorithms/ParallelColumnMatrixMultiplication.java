package Algorithms;

import Matrix.IMatrix;
import Matrix.Matrix;

public class ParallelColumnMatrixMultiplication implements IMatrixMultiplication{
    public IMatrix Multiply(IMatrix A, IMatrix B) {

        int[][] matrixC = new int[A.getRowNumber()][A.getColumnNumber()];

        var matrixA = A.getMatrix();
        var matrixB = B.getMatrix();

        var size = A.getRowNumber();

        var columns = new SyncColumn[size];
        for (int i = 0; i < columns.length; i++) {
            var col = new int [size];
            for (int j = 0; j < size; j++) {
                col[j] = matrixB[j][i];
            }
            columns[i] = new SyncColumn();
            columns[i].column = col;
        }

        for (int i = 0; i < columns.length; i++) {
            columns[i].nextColumn = columns[(i + 1 + size)%size];
        }
        var threads = new Thread[size];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MatrixThread(i, matrixA[i], columns[i], columns[i].nextColumn, matrixC));
            threads[i].start();
        }
        for (Thread thread : threads ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return new Matrix(matrixC);
    }
}

class SyncColumn {
    public boolean isReady = false;
    public int[] column;
    public SyncColumn nextColumn;
}

class MatrixThread implements Runnable {
    private int row;
    private int[] rowA;
    private SyncColumn columnB;
    private SyncColumn nextColumn;
    private int[][] matrixC;
    public MatrixThread(int rowNumber, int[] rowA, SyncColumn columnB, SyncColumn nextColumn, int[][] matrixC) {
        this.row = rowNumber;
        this.rowA = rowA;
        this.columnB = columnB;
        this.nextColumn = nextColumn;
        this.matrixC = matrixC;
    }
    @Override
    public void run() {
        var size = rowA.length;

        for (int iteration = 0; iteration < size; iteration++) {
            synchronized (columnB) {
                columnB.isReady = false;
            }
            var col = (row - iteration + size)%size;

            var calculatedElement = 0;
            for (int k = 0; k < size; k++) {
                calculatedElement += rowA[k] * columnB.column[k];
            }
            matrixC[row][col] = calculatedElement;
            synchronized (columnB) {
                columnB.isReady = true;
            }
            synchronized (nextColumn) {
                while (!nextColumn.isReady) {
                    try {
                        nextColumn.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                synchronized (columnB) {
                    columnB.notifyAll();
                }

                columnB = nextColumn;
                nextColumn = nextColumn.nextColumn;
            }
        }
    }
}