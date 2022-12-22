import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import t2_Sort.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class t2_SortTests {

    static Stream<Arguments> valuesForSort() {
        return Stream.of(
                Arguments.of(new String[]{"A", "B", "C", "D", "E"}, new String[]{"A", "B", "C", "D", "E"}),
                Arguments.of(new String[]{"A", "D", "B", "C", "E"}, new String[]{"A", "B", "C", "D", "E"}),
                Arguments.of(new String[]{"E", "C", "D", "B", "A"}, new String[]{"A", "B", "C", "D", "E"}),
                Arguments.of(new String[]{"E", "D", "C", "B", "A"}, new String[]{"A", "B", "C", "D", "E"})
        );
    }

    @ParameterizedTest
    @MethodSource("valuesForSort")
    <T extends Comparable> void quickSort(T[] ints, T[] target) {
        assertArrayEquals(target, Sort.quickSort(ints));
    }

    @ParameterizedTest
    @MethodSource("valuesForSort")
    <T extends Comparable> void mergeSort(T[] ints, T[] target) {
        assertArrayEquals(target, Sort.quickSort(ints));
    }
}
