package com.synalogik.core;

import java.util.*;

public class WordLengthController
{
    private final Map<Integer, Integer> wordLengthFrequencies;

    public WordLengthController()
    {
        this.wordLengthFrequencies = new HashMap<>();
    }

    public void encounteredWordLength(int length)
    {
        wordLengthFrequencies.merge(length, 1, (ignored, currentFrequency) -> currentFrequency + 1);
    }

    public double averageWordLength()
    {
        if (wordLengthFrequencies.isEmpty())
        {
            return 0;
        }

        int totalNumberOfCharacters = 0;
        int totalNumberOfWords = 0;
        for (Map.Entry<Integer, Integer> wordLengthFrequency : wordLengthFrequencies.entrySet())
        {
            Integer length = wordLengthFrequency.getKey();
            Integer frequency = wordLengthFrequency.getValue();

            totalNumberOfCharacters += length * frequency;
            totalNumberOfWords += frequency;
        }

        return (double) totalNumberOfCharacters / totalNumberOfWords;
    }

    public Set<Integer> mostFrequentWordLengths()
    {
        Optional<Integer> highestFrequency = wordLengthFrequencies.values().stream().max(Comparator.naturalOrder());

        if (highestFrequency.isEmpty())
        {
            return Collections.emptySet();
        }

        Set<Integer> mostFrequentWordLengths = new HashSet<>();

        for (Map.Entry<Integer, Integer> wordLengthFrequency : wordLengthFrequencies.entrySet())
        {
            Integer frequency = wordLengthFrequency.getValue();

            if (frequency.equals(highestFrequency.get()))
            {
                mostFrequentWordLengths.add(wordLengthFrequency.getKey());
            }
        }

        return mostFrequentWordLengths;
    }

    public String report()
    {
        return null;
    }
}
