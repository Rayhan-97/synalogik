package com.synalogik.core;

import java.util.*;
import java.util.stream.Collectors;

public class WordLengthController
{
    private final Map<Integer, Long> wordLengthFrequencies;
    private long totalWordCount;
    private long highestFrequency;

    public WordLengthController()
    {
        this.wordLengthFrequencies = new HashMap<>();
    }

    public void encounteredWordLength(int length)
    {
        totalWordCount++;
        wordLengthFrequencies.merge(length, 1L, (currentFrequency, ignored) -> currentFrequency + 1);
        highestFrequency = Math.max(highestFrequency, wordLengthFrequencies.get(length));
    }

    public double averageWordLength()
    {
        if (wordLengthFrequencies.isEmpty())
        {
            return 0;
        }

        long totalNumberOfCharacters = 0;
        for (Map.Entry<Integer, Long> wordLengthFrequency : wordLengthFrequencies.entrySet())
        {
            Integer length = wordLengthFrequency.getKey();
            Long frequency = wordLengthFrequency.getValue();

            totalNumberOfCharacters += length * frequency;
        }

        return (double) totalNumberOfCharacters / totalWordCount;
    }

    public Set<Integer> mostFrequentWordLengths()
    {
        if (totalWordCount == 0)
        {
            return Collections.emptySet();
        }

        Set<Integer> mostFrequentWordLengths = new HashSet<>();

        for (Map.Entry<Integer, Long> wordLengthFrequency : wordLengthFrequencies.entrySet())
        {
            Long frequency = wordLengthFrequency.getValue();

            if (frequency.equals(highestFrequency))
            {
                mostFrequentWordLengths.add(wordLengthFrequency.getKey());
            }
        }

        return mostFrequentWordLengths;
    }

    public String report()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Word count = %d\n".formatted(totalWordCount));

        if (totalWordCount == 0)
        {
            return sb.toString().stripTrailing();
        }

        sb.append("Average word length = %s\n".formatted(formatAverage(averageWordLength())));

        wordLengthFrequencies.forEach(
                (length, frequency) -> sb.append("Number of words of length %d is %d\n".formatted(length, frequency))
        );

        String mostFrequentWordLengthsAsString = mostFrequentWordLengths().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" & "));
        sb.append("The most frequently occurring word length is %d, for words of length %s".formatted(highestFrequency, mostFrequentWordLengthsAsString));

        return sb.toString();
    }

    private static String formatAverage(double average)
    {
        boolean isAverageWholeNumber = (long) average == average;

        return isAverageWholeNumber ? Long.toString((long) average) : String.format("%.3f", average);
    }
}
