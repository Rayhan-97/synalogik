package com.synalogik.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WordLengthControllerTest
{
    private final WordLengthController wordLengthController = new WordLengthController();

    @Test
    void givenNoWordsEncountered_whenAverageWordLength_thenZero()
    {
        double averageWordLength = wordLengthController.averageWordLength();

        assertThat(averageWordLength).isEqualTo(0);
    }

    @Test
    void givenNoWordsEncountered_whenMostFrequentWordLength_thenEmptySet()
    {
        Set<Integer> mostFrequentWordLengths = wordLengthController.mostFrequentWordLengths();

        assertThat(mostFrequentWordLengths).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("wordLengthInputs")
    void givenWordLengths_whenAverageAndMostFrequentWordLength_thenCorrect(List<Integer> wordLengths, double expectedAverageWordLength, Set<Integer> expectedMostFrequentWordLengths)
    {
        wordLengths.forEach(wordLengthController::encounteredWordLength);

        double averageWordLength = wordLengthController.averageWordLength();
        Set<Integer> mostFrequentWordLengths = wordLengthController.mostFrequentWordLengths();

        assertThat(averageWordLength).isEqualTo(expectedAverageWordLength, withPrecision(0.001));
        assertThat(mostFrequentWordLengths).containsExactlyInAnyOrderElementsOf(expectedMostFrequentWordLengths);
    }

    private static Stream<Arguments> wordLengthInputs()
    {
        return Stream.of(
                arguments(List.of(1), 1.0, Set.of(1)),
                arguments(List.of(1, 2), 1.5, Set.of(1, 2)),
                arguments(List.of(1, 1, 2), 1.333, Set.of(1)),
                arguments(List.of(5, 5, 1, 4, 7, 3, 4, 2, 10), 4.556, Set.of(5, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("reportInputs")
    void givenWordLengths_whenReport_thenCorrect(List<Integer> wordLengths, String expectedReport)
    {
        wordLengths.forEach(wordLengthController::encounteredWordLength);

        String report = wordLengthController.report();

        assertThat(report).isEqualTo(expectedReport);
    }

    private static Stream<Arguments> reportInputs()
    {
        return Stream.of(
                arguments(List.of(), "Word count = 0"),
                arguments(List.of(1), """
                        Word count = 1
                        Average word length = 1
                        Number of words of length 1 is 1
                        The most frequently occurring word length is 1, for words of length 1"""),
                arguments(List.of(5, 5, 1, 4, 7, 3, 4, 2, 10), """
                        Word count = 9
                        Average word length = 4.556
                        Number of words of length 1 is 1
                        Number of words of length 2 is 1
                        Number of words of length 3 is 1
                        Number of words of length 4 is 2
                        Number of words of length 5 is 2
                        Number of words of length 7 is 1
                        Number of words of length 10 is 1
                        The most frequently occurring word length is 2, for words of length 4 & 5""")
        );
    }
}