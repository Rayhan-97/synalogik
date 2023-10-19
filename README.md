# README

## Word definition

Words are delimited by spaces.\
Alphanumeric characters and ampersands contribute to word length.\
Everything else is ignored including brackets, punctuation and other symbols.\
Hyphens and apostrophes do not contribute to word length either.

Formatted numbers may be of the form:

- 123
- 1.201
- 10,000
- £5.21
- Dates in the form:
    - dd-mm-yy
    - dd-mm-yyyy
    - yyyy-mm-dd
    - . and / may also be used as delimiters

Currency symbols and commas do not count to the length of a word.\
In date form, the delimiters do contribute to the length of the word,
whereas they would for non-date words.
There are date forms not accounted for (such as yyyy-MM-dd'T'HH:mm:ss.SSSZ) in which case the date will
be treated as a non-date word, thus the delimiters will not contribute to the length of the word.

Refer to the test cases for
[valid words](src/test/java/com/synalogik/core/WordTokenTest.java) and
[valid numbers/dates](src/test/java/com/synalogik/core/NumberTokenTest.java).

## Running the program

From the terminal, in the root directory of the project,
running the following command will analyse the words in the specified file.
Make sure to specify the absolute path.
You can comma-separate multiple filepaths.

``` 
  mvn compile exec:java -Dexec.arguments="/tmp/bible_daily.txt"
```

Alternatively, you can copy your desired text files to `src/main/resources/` directory.
You can run the program without arguments in CLI mode which will allow you to analyse files in the `resources/`
directory.

```
  mvn compile exec:java
```

A prompt will be displayed:

```text
------------------------------------------------------------

Available files:

0. example.txt
1. bible_daily.txt

Enter the number of the file you want to analyse (q to quit): 
```