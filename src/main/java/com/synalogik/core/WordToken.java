package com.synalogik.core;

import java.util.Objects;

public class WordToken implements Token
{
    private static final String INVALID_CHARACTERS_IN_WORD = "[^a-zA-Z0-9&]";

    private final String word;

    public WordToken(String word)
    {
        Objects.requireNonNull(word);
        word = word.replaceAll(INVALID_CHARACTERS_IN_WORD, "");

        this.word = word;
    }

    @Override
    public boolean isValid()
    {
        return !word.isEmpty();
    }

    @Override
    public int length()
    {
        return word.length();
    }
}
