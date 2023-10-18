package com.synalogik.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class NumberTokenTest
{
    @Test
    void givenNull_whenConstructingNumberToken_thenThrowNullPointerException()
    {
        assertThatThrownBy(() -> new NumberToken(null))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"notANumber", ",1234", "2-10-2017"})
    void givenInvalidWord_whenConstructingNumberToken_thenThrowIllegalArgumentException(String invalidNumber)
    {
        assertThatThrownBy(() -> new NumberToken(invalidNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void givenNumbers_whenLength_thenCorrectLength(String number, int expectedLength)
    {
        Token token = new NumberToken(number);

        int length = token.length();

        assertThat(length).isEqualTo(expectedLength);
    }

    private static Stream<Arguments> inputs()
    {
        return Stream.of(
                arguments("1", 1),
                arguments("12345", 5),
                arguments("1.201", 5),
                arguments("10,000", 5),
                arguments("£12", 2),
                arguments("$14,000.50", 8),
                arguments("18/06/2016", 10),
                arguments("18.06.2016", 10),
                arguments("18-06-2016", 10),
                arguments("18-06-16", 8),
                arguments("18-06/2016", 10),
                arguments("2018-06-16", 10)
        );
    }
}