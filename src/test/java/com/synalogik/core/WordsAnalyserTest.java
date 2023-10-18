package com.synalogik.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WordsAnalyserTest
{
    private final WordsAnalyser wordsAnalyser = new WordsAnalyser(new WordLengthController());

    @ParameterizedTest
    @MethodSource("filenameInputs")
    void givenWordLengths_whenReport_thenCorrect(String filename, String expectedReport) throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        String report = wordsAnalyser.analyseFile(file);

        assertThat(report).isEqualTo(expectedReport);
    }

    private static Stream<Arguments> filenameInputs()
    {
        return Stream.of(
                arguments("empty.txt", "Word count = 0"),
                arguments("one_character.txt", """
                        Word count = 1
                        Average word length = 1
                        Number of words of length 1 is 1
                        The most frequently occurring word length is 1, for words of length 1"""),
                arguments("multi-line.txt", """
                        Word count = 10
                        Average word length = 6.100
                        Number of words of length 4 is 1
                        Number of words of length 5 is 3
                        Number of words of length 6 is 3
                        Number of words of length 8 is 3
                        The most frequently occurring word length is 3, for words of length 5 & 6 & 8"""),
                arguments("malformed.txt", """
                        Word count = 6
                        Average word length = 5.333
                        Number of words of length 2 is 1
                        Number of words of length 4 is 2
                        Number of words of length 5 is 1
                        Number of words of length 7 is 1
                        Number of words of length 10 is 1
                        The most frequently occurring word length is 2, for words of length 4"""),
                arguments("example.txt", """
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