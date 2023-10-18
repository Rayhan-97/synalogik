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

class WordTokenTest
{
    @Test
    void givenNullWord_whenConstructingWordToken_thenThrowNullPointerException()
    {
        assertThatThrownBy(() -> new WordToken(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenEmptyWord_whenLength_thenReturnZero()
    {
        Token token = new WordToken("");

        int length = token.length();

        assertThat(length).isZero();
    }

    @Test
    void givenAmpersandWord_whenLength_thenReturnOne()
    {
        Token token = new WordToken("&");

        int length = token.length();

        assertThat(length).isEqualTo(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ".",
            ",",
            "-",
            "?",
            "!",
            "@",
            "%",
            "_",
            "-",
            "+",
            "*",
            "=",
            "^",
            "|",
            "/",
            "£",
            "€",
            "#",
            "\"",
            "'"
    })
    void givenInvalidOneCharacterLengthWords_whenLength_thenReturnZero(String word)
    {
        Token token = new WordToken(word);

        int length = token.length();

        assertThat(length).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"()", "(.)", "({[<>]})", "({[<.}>,<[>]})"})
    void givenInvalidWordsWithBrackets_whenLength_thenReturnZero(String word)
    {
        Token token = new WordToken(word);

        int length = token.length();

        assertThat(length).isZero();
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void givenValidWords_whenLength_thenCorrectLength(String word, int expectedLength)
    {
        Token token = new WordToken(word);

        int length = token.length();

        assertThat(length).isEqualTo(expectedLength);
    }

    private static Stream<Arguments> inputs()
    {
        return Stream.of(
                arguments("a", 1),
                arguments("hello", 5),
                arguments("&", 1),
                arguments("morning.", 7),
                arguments("(e.g.", 2),
                arguments("end)", 3),
                arguments("co-operate", 9),
                arguments("it's", 3),
                arguments("if/when", 6)
        );
    }
}