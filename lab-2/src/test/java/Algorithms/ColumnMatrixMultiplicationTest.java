package Algorithms;

import Matrix.MatrixGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ColumnMatrixMultiplicationTest {
    protected static int[][] simpleMultiplication3x3Result = {
            {30, 36, 42},
            {66, 81, 96},
            {102, 126, 150}
    };
    protected static int[][] simpleMultiplication4x4Result = {
            {90, 100, 110, 120},
            {202, 228, 254, 280},
            {314, 356, 398, 440},
            {426, 484, 542, 600}
    };

    // Column
    protected static IMatrixMultiplication columnAlgo = new ColumnMatrixMultiplication();

    protected static IMatrixMultiplication parallelColumnAlgo2Threads = new ParallelColumnMatrixMultiplicationV1(2);
    protected static IMatrixMultiplication parallelColumnAlgo3Threads = new ParallelColumnMatrixMultiplicationV1(3);
    protected static IMatrixMultiplication parallelColumnAlgo4Threads = new ParallelColumnMatrixMultiplicationV1(4);

    // Fox
    protected static IMatrixMultiplication foxAlgo2 = new FoxMatrixMultiplication(2);
    protected static IMatrixMultiplication foxAlgo3 = new FoxMatrixMultiplication(3);
    protected static IMatrixMultiplication foxAlgo4 = new FoxMatrixMultiplication(4);

    protected static IMatrixMultiplication parallelFoxAlgo2Threads = new ParallelFoxMatrixMultiplication(2);
    protected static IMatrixMultiplication parallelFoxAlgo3Threads = new ParallelFoxMatrixMultiplication(3);
    protected static IMatrixMultiplication parallelFoxAlgo4Threads = new ParallelFoxMatrixMultiplication(4);
    @ParameterizedTest
    @MethodSource("provideMatrices")
    void Multiply_Simple3x3Matrices_ShouldBeCorrect3x3Result(IMatrixMultiplication algo, int[][] correctResult, int simpleMatrixSize) {

        var simpleMatrixA = MatrixGenerator.getSimpleMatrix(simpleMatrixSize);
        var simpleMatrixB = MatrixGenerator.getSimpleMatrix(simpleMatrixSize);

        var matrixC = algo.Multiply(simpleMatrixA, simpleMatrixB);

        for (int i = 0; i < simpleMatrixSize; i++) {
            for (int j = 0; j < simpleMatrixSize; j++) {
                assertEquals(matrixC.getMatrix()[i][j], correctResult[i][j]);
            }
        }
    }

    static Stream<Arguments> provideMatrices() {
        return Stream.of(
                Arguments.of(columnAlgo, simpleMultiplication3x3Result, 3),
                Arguments.of(columnAlgo, simpleMultiplication4x4Result, 4),

                Arguments.of(parallelColumnAlgo3Threads, simpleMultiplication3x3Result, 3),
                Arguments.of(parallelColumnAlgo2Threads, simpleMultiplication4x4Result, 4),
                Arguments.of(parallelColumnAlgo4Threads, simpleMultiplication4x4Result, 4),

                Arguments.of(foxAlgo3, simpleMultiplication3x3Result, 3),
                Arguments.of(foxAlgo2, simpleMultiplication4x4Result, 4),
                Arguments.of(foxAlgo4, simpleMultiplication4x4Result, 4),

                Arguments.of(parallelFoxAlgo3Threads, simpleMultiplication3x3Result, 3),
                Arguments.of(parallelFoxAlgo2Threads, simpleMultiplication4x4Result, 4),
                Arguments.of(parallelFoxAlgo4Threads, simpleMultiplication4x4Result, 4)
        );
    }
}