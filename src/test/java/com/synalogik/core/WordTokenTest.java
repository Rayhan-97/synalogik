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
    void givenNullWord_whenConstructingWordToken_thenNullPointerException()
    {
        assertThatThrownBy(() -> new WordToken(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenEmptyWord_whenIsValid_thenFalse()
    {
        WordToken wordToken = new WordToken("");

        boolean valid = wordToken.isValid();

        assertThat(valid).isFalse();
    }

    @Test
    void givenAmpersandWord_whenIsValid_thenTrue()
    {
        WordToken wordToken = new WordToken("&");

        boolean valid = wordToken.isValid();

        assertThat(valid).isTrue();
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
    void givenInvalidOneCharacterLengthWords_whenIsValid_thenFalse(String word)
    {
        WordToken wordToken = new WordToken(word);

        boolean valid = wordToken.isValid();

        assertThat(valid).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"()", "(.)", "({[<>]})", "({[<.}>,<[>]})"})
    void givenInvalidWordsWithBrackets_whenIsValid_thenFalse(String word)
    {
        WordToken wordToken = new WordToken(word);

        boolean valid = wordToken.isValid();

        assertThat(valid).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "hello", "Morning."})
    void givenValidWords_whenIsValid_thenTrue(String word)
    {
        WordToken wordToken = new WordToken(word);

        boolean valid = wordToken.isValid();

        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void givenValidWords_whenLength_thenCorrectLength(String word, int expectedLength)
    {
        WordToken wordToken = new WordToken(word);

        int length = wordToken.length();

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