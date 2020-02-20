/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static final String A = "/home/yfouquer/hashcode/data/a_example.txt";
    public static final String B = "/home/yfouquer/hashcode/data/b_read_on.txt";
    public static final String C = "/home/yfouquer/hashcode/data/c_incunabula.txt";
    public static final String D = "/home/yfouquer/hashcode/data/d_tough_choices.txt";
    public static final String E = "/home/yfouquer/hashcode/data/e_so_many_books.txt";
    public static final String F = "/home/yfouquer/hashcode/data/f_libraries_of_the_world.txt";
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
        loadData(B);


    }
}
