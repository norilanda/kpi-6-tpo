import ProducerConsumerNumbers.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static ProducerConsumerNumbers.Main.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerConsumerTests {

    static Stream<Arguments> provideParameters() {
        return Stream.of(
                // bufferSize, numberToGeneratePerProducer, producersNumber, consumerNumbers
                Arguments.of(10, 5, 3, 4),
                Arguments.of(100, 50, 8, 1),
                Arguments.of(100, 50, 4, 4),
                Arguments.of(100, 110, 1, 8),

                Arguments.of(500, 150, 8, 1),
                Arguments.of(500, 150, 4, 4),
                Arguments.of(500, 510, 1, 8),

                Arguments.of(1000, 350, 8, 1),
                Arguments.of(1000, 550, 4, 4),
                Arguments.of(1000, 1110, 1, 8)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void producerConsumerTest(
            int bufferSize, int numberToGeneratePerProducer,
            int producersNumber, int consumerNumbers) {
        var results = demo(bufferSize, numberToGeneratePerProducer, producersNumber, consumerNumbers);

        AssertCorrect(results, producersNumber);
    }

    public static void AssertCorrect(int[] takenNumberCounts, int producerNumber) {
        for (var number : takenNumberCounts) {
            assertEquals(number, producerNumber);
        }
    }
}
