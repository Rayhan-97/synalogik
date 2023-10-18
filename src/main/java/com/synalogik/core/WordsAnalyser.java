package com.synalogik.core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WordsAnalyser
{
    private final WordLengthController wordLengthController;

    public WordsAnalyser(WordLengthController wordLengthController)
    {
        this.wordLengthController = wordLengthController;
    }

    public String analyseFile(File file) throws IOException
    {
        Scanner input = new Scanner(file);
        input.useDelimiter(Pattern.compile("\\s+"));

        while (input.hasNext())
        {
            String candidateWord = input.next();
            if (candidateWord.isBlank())
            {
                continue;
            }

            Token token = TokenFactory.from(candidateWord);
            if (token.length() > 0)
            {
                wordLengthController.encounteredWordLength(token.length());
            }
        }

        return wordLengthController.report();
    }
}
