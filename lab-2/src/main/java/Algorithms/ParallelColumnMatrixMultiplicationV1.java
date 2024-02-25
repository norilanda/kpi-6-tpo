package Algorithms;

import Matrix.IMatrix;
import Matrix.Matrix;
import Matrix.MatrixHelper;

public class ParallelColumnMatrixMultiplicationV1 implements IMatrixMultiplication{
    private int threadsNumber = 1;

    public ParallelColumnMatrixMultiplicationV1() { }
    public ParallelColumnMatrixMultiplicationV1 (int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }
    @Override
    public IMatrix Multiply(IMatrix A, IMatrix B) {
        int[][] matrixC = new int[A.getRowNumber()][A.getColumnNumber()];

        var matrixA = A.getMatrix();
        var matrixB = B.getMatrix();

        var size = A.getRowNumber();

        var columns = new SyncColumns[threadsNumber];
        int colsPerFragment = size / threadsNumber; //TODO: make work when cannot divide equally

        for (int i = 0; i < threadsNumber; i++) {
            int[][] colsForThread = new int[colsPerFragment][size];
            for (int j = 0; j < colsPerFragment; j++) {
                colsForThread[j] = MatrixHelper.copyColumn(matrixB, i*colsPerFragment + j);
            }
            columns[i] = new SyncColumns();
            columns[i].columns = colsForThread;
        }

        for (int i = 0; i < threadsNumber; i++) {
            columns[i].nextColumn = columns[(i + 1 + threadsNumber) % threadsNumber];
        }
        var threads = new Thread[threadsNumber];
        for (int i = 0; i < threads.length; i++) {
            int[][] rowsForThread = new int[colsPerFragment][size];
            var rowIndexStart = i*colsPerFragment;
            for (int j = 0; j < colsPerFragment; j++) {
                rowsForThread[j] = matrixA[rowIndexStart + j];
            }
            var matrixThread = new MatrixThreadV1(rowIndexStart, rowsForThread,
                    columns[i], columns[i].nextColumn, matrixC, threadsNumber);
            threads[i] = new Thread(matrixThread);
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

class SyncColumns {
    public boolean isReady = false;
    public int[][] columns;
    public SyncColumns nextColumn;
}

class MatrixThreadV1 implements Runnable {
    private int rowIndexStart;
    private int[][] rowsA;
    private SyncColumns columnsB;
    private SyncColumns nextColumns;
    private int threadNumber;
    private int[][] matrixC;
    public MatrixThreadV1(int rowNumber, int[][] rowsA,
                          SyncColumns columnsB, SyncColumns nextColumns,
                          int[][] matrixC, int threadNumber) {
        this.rowIndexStart = rowNumber;
        this.rowsA = rowsA;
        this.columnsB = columnsB;
        this.nextColumns = nextColumns;
        this.matrixC = matrixC;
        this.threadNumber = threadNumber;
    }
    @Override
    public void run() {
        var matrixSize = rowsA[0].length;

        for (int iteration = 0; iteration < threadNumber; iteration++) {
            synchronized (columnsB) {
                columnsB.isReady = false;
            }
            for (int row = 0; row < rowsA.length; row++) {
                var rowIndex = rowIndexStart + row;
//                System.out.println(row + ") rowIndex: " + rowIndex);

                for (int col = 0; col < columnsB.columns.length; col++) {
                    var colIndex = (rowIndexStart + iteration * columnsB.columns.length + matrixSize) % matrixSize + col;
//                    System.out.print(col + ") colIndex: " + colIndex);

                    var calculatedElement = MatrixHelper.calculateMatrixElement(rowsA[row], columnsB.columns[col]);
                    matrixC[rowIndex][colIndex] = calculatedElement;
//                    System.out.println(Thread.currentThread().getName() + ": " + rowIndex + " " + colIndex);
                }
            }

            synchronized (columnsB) {
                columnsB.isReady = true;
                columnsB.notifyAll();
            }
            synchronized (nextColumns) {
                while (!nextColumns.isReady) {
                    try {
                        nextColumns.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            columnsB = nextColumns;
            nextColumns = nextColumns.nextColumn;
        }
    }
}