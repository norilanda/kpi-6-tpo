package Benchmarks;

import Algorithms.*;
import Matrix.IMatrix;
import Matrix.MatrixGenerator;

public class BenchmarksRunner {
    public static void Run(){
        int iterations = 5;

//        int[] matrixSizes = {600, 1000, 1600, 2000, 2600, 3000};
        int[] matrixSizes = {600};
        int[] threadsNumbers = {2, 4, 8};

        for (int size : matrixSizes) {
            var algo = new ColumnMatrixMultiplication();
            RunBenchmark(size, iterations, algo, "column algo");
        }

        for (int threadNum : threadsNumbers) {
            for (int size : matrixSizes) {
                var algo = new ParallelColumnMatrixMultiplicationV1(threadNum);
                RunBenchmark(size, iterations, algo, "parallel column " + threadNum + " threads");
            }
        }

        for (int size : matrixSizes) {
            var algo = new FoxMatrixMultiplication(4);
            RunBenchmark(size, iterations, algo, "fox algo");
        }

        for (int blocksPerRow : threadsNumbers) {
            for (int size : matrixSizes) {
                var algo = new ParallelFoxMatrixMultiplication(blocksPerRow);
                RunBenchmark(size, iterations, algo, "parallel fox " + blocksPerRow*blocksPerRow + " threads");
            }
        }
    }

    public static void RunBenchmark(int matrixSize, int iterations, IMatrixMultiplication algo, String algoName) {
        int numberToFill = 1;

        IMatrix A = MatrixGenerator.generateZerosMatrixWithDiagonal(matrixSize, numberToFill);
        IMatrix B = MatrixGenerator.generateZerosMatrixWithDiagonal(matrixSize, numberToFill);

        var parallelColumnMultiplicationTime = MatrixMultiplicationBenchmark
                .GetMatrixMultiplicationTimeMilliseconds(algo, A, B, iterations);
        var timeInSeconds = parallelColumnMultiplicationTime / 1000;

        System.out.println("size = " + matrixSize + " | " + algoName + ": "
                +  timeInSeconds  + " s");
    }
}
