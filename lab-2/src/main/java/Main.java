import Algorithms.*;
import Benchmarks.MatrixMultiplicationBenchmark;
import IO.ConsoleMatrixPrinter;
import IO.IMatrixPrinter;
import Matrix.IMatrix;
import Matrix.Matrix;
import Matrix.MatrixGenerator;

public class Main {
    public static void main(String[] args) {
        IMatrixPrinter printer = new ConsoleMatrixPrinter();

        int matrixSize = 2000;
        int numberToFill = 1;
//        IMatrix A = MatrixGenerator.generateMatrix(matrixSize, numberToFill);
        IMatrix A = MatrixGenerator.generateZerosMatrixWithDiagonal(matrixSize, numberToFill);
//        IMatrix B = MatrixGenerator.generateMatrix(matrixSize, numberToFill);
        IMatrix B = MatrixGenerator.generateZerosMatrixWithDiagonal(matrixSize, numberToFill);
        IMatrix C = null;
//        printer.PrintMatrix(A);
        int iterations = 10;

//        var columnMultiplication = new ColumnMatrixMultiplication();
//        var columnMultiplicationTime = MatrixMultiplicationBenchmark
//                .GetMatrixMultiplicationTimeMilliseconds(columnMultiplication, A, B, iterations);
//        System.out.println(columnMultiplicationTime);

        var parallelColumnMultiplication = new ParallelColumnMatrixMultiplicationV1(2);
        var parallelColumnMultiplicationTime = MatrixMultiplicationBenchmark
                .GetMatrixMultiplicationTimeMilliseconds(parallelColumnMultiplication, A, B, iterations);
        System.out.println(parallelColumnMultiplicationTime);

//        C = columnMultiplication.Multiply(A, B);
//        C = parallelColumnMultiplication.Multiply(A, B);

        // =======================================
//        int blocksPerRow = 8;
//        var foxMultiplication = new FoxMatrixMultiplication(blocksPerRow);
//        var foxColumnMultiplicationTime = MatrixMultiplicationBenchmark
//                .GetMatrixMultiplicationTimeMilliseconds(foxMultiplication, A, B, iterations);
//        System.out.println(foxColumnMultiplicationTime);



        // =======================================
//        var parallelFoxMultiplication = new ParallelFoxMatrixMultiplication(blocksPerRow);
//        var parallelFoxColumnMultiplicationTime = MatrixMultiplicationBenchmark
//                .GetMatrixMultiplicationTimeMilliseconds(parallelFoxMultiplication, A, B, iterations);
//        System.out.println(parallelFoxColumnMultiplicationTime);

        var simpleMatrixSize = 6;
//        var simpleMatrixA = MatrixGenerator.getSimpleMatrix(simpleMatrixSize);
//        var simpleMatrixB = MatrixGenerator.getSimpleMatrix(simpleMatrixSize);
//
//        C = parallelColumnMultiplication.Multiply(simpleMatrixA, simpleMatrixB);
//        var d = C;

//        printer.PrintMatrix(C);
    }
}