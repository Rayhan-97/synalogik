package com.synalogik.core;

import java.util.Objects;
import java.util.regex.Pattern;

public class NumberToken implements Token
{
    private static final String INVALID_CHARACTERS_IN_NUMBER = "[\\p{Sc},]";
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\p{Sc}?\\d+(,?\\d+)*(\\.?\\d+)?$");
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^(\\d{2}[./-]\\d{2}[./-](\\d{2}|\\d{4}))|((\\d{2}|\\d{4})[./-]\\d{2}[./-]\\d{2})$");

    private final String word;

    public NumberToken(String number)
    {
        Objects.requireNonNull(number);
        if (!isNumber(number))
        {
            throw new IllegalArgumentException("Invalid number provided: %s".formatted(number));
        }

        number = number.replaceAll(INVALID_CHARACTERS_IN_NUMBER, "");

        this.word = number;
    }

    public static boolean isNumber(String candidateNumber)
    {
        return NUMBER_PATTERN.matcher(candidateNumber).matches() || DATE_PATTERN.matcher(candidateNumber).matches();
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
