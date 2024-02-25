package Benchmarks;

import Algorithms.IMatrixMultiplication;
import Matrix.IMatrix;

public class MatrixMultiplicationBenchmark {
    public static double GetMatrixMultiplicationTimeMilliseconds(IMatrixMultiplication algo,
                                                              IMatrix A, IMatrix B, int iterations) {
        double totalTimeNanoseconds = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            algo.Multiply(A, B);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            totalTimeNanoseconds += duration;
        }
        return totalTimeNanoseconds / iterations / 1000000;
    }
}
