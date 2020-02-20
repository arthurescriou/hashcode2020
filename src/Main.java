/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    static List<Books> books = new ArrayList<>();
    static List<Library> libraries = new ArrayList<>();
    private static int daysForScanning;

    private static void loadData(String file) {
        try {
            List<String> collect = Files.lines(Paths.get(file)).collect(Collectors.toList());

            String[] s = collect.get(0).split(" ");
            int nbBooks = Integer.parseInt(s[0]);
            int nbLibrary = Integer.parseInt(s[0]);
            daysForScanning = Integer.parseInt(s[0]);

            String[] s1 = collect.get(1).split(" ");
            for (int i = 0; i < s1.length; i++) {
                books.add(new Books(i, Integer.parseInt(s1[i])));
            }

            collect.remove(0);
            collect.remove(0);

            int libraryId = 0;
            for (int i = 0; i < collect.size(); i = i + 2) {
                String[] libraryStuff = collect.get(i).split(" ");
                String[] booksInLibrary = collect.get(i + 1).split(" ");

                Map<Integer, Books> booksinLib = Arrays.stream(booksInLibrary)
                        .map(st -> books.get(Integer.parseInt(st)))
                        .collect(Collectors.toMap(Books::getId, Function.identity()));

                libraries.add(new Library(libraryId++, booksinLib, Integer.parseInt(libraryStuff[1]),
                        Integer.parseInt(libraryStuff[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadData("/home/yfouquer/hashcode/src/a_example.txt");

        books.forEach(System.out::println);
        System.out.println();

        libraries.forEach(System.out::println);
    }
}
