package com.synalogik.core;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordsAnalyserEntrypoint
{
    public static void main(String[] args) throws IOException
    {
        if (args.length > 0)
        {
            analyseSuppliedFiles(args);
        }
        else
        {
            startCommandLineInterface();
        }
    }

    private static void analyseSuppliedFiles(String[] args) throws IOException
    {
        for (String absolutePath : args)
        {
            File file = new File(absolutePath);

            if (!file.isFile())
            {
                System.out.println("File does not exist " + absolutePath);
                System.out.println("Make sure to use the absolute path to the file");
                System.exit(1);
            }

            WordsAnalyser analyser = new WordsAnalyser(new WordLengthController());

            System.out.printf("Analysing file %s...%n%n", file.getName());
            String reportAnalysis = analyser.analyseFile(file);
            System.out.println(reportAnalysis);
        }
    }

    private static void startCommandLineInterface() throws IOException
    {
        ClassLoader classLoader = WordsAnalyserEntrypoint.class.getClassLoader();
        File resourceDirectory = new File(Objects.requireNonNull(classLoader.getResource("")).getFile());
        List<File> files = Arrays.stream(Objects.requireNonNull(resourceDirectory.listFiles()))
                .filter(File::isFile)
                .toList();

        Map<Integer, File> fileMap = IntStream.range(0, files.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), files::get));

        Scanner input = new Scanner(System.in);
        WordsAnalyser analyser = new WordsAnalyser(new WordLengthController());
        String fileNumber;
        do
        {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("\nAvailable files:\n");
            fileMap.forEach((i, file) -> System.out.printf("%d. %s%n", i, file.getName()));
            System.out.print("\nEnter the number of the file you want to analyse (q to quit): ");

            fileNumber = input.next();

            if (!"q".equals(fileNumber))
            {
                File file = fileMap.get(Integer.valueOf(fileNumber));
                if (file == null)
                {
                    System.out.println("Invalid choice");
                }
                else
                {
                    System.out.printf("Analysing file %s...%n%n", file.getName());
                    String reportAnalysis = analyser.analyseFile(file);
                    System.out.println(reportAnalysis);
                }
            }

        } while (!"q".equals(fileNumber));

        input.close();
    }
}
