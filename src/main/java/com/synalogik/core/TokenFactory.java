package com.synalogik.core;

public class TokenFactory
{
    public static Token from(String candidateWord)
    {
        return NumberToken.isNumber(candidateWord) ? new NumberToken(candidateWord) : new WordToken(candidateWord);
    }
}
