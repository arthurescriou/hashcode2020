/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sun.org.apache.xpath.internal.operations.Or;

public class Main {

    public static final String A = "data/a_example.txt";
    public static final String B = "data/b_read_on.txt";
    public static final String C = "data/c_incunabula.txt";
    public static final String D = "data/d_tough_choices.txt";
    public static final String E = "data/e_so_many_books.txt";
    public static final String F = "data/f_libraries_of_the_world.txt";
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
        loadData(A);

        final int[] accuDaysSignUp = { 0 };
        List<Library> OrderedSelectedLibrary =
                libraries.stream().sorted(Comparator.comparingDouble(Main::scoreMoyenLibrary)).filter(library -> {
                    accuDaysSignUp[0] += library.getSignUpProcess();
                    return accuDaysSignUp[0] < daysForScanning;
                }).collect(Collectors.toList());

        printResult(OrderedSelectedLibrary);

    }

    private static void printResult(List<Library> lib) {
        try {

            FileWriter writer = new FileWriter(new File(A + "_result.txt"));
            writer.write(lib.size()+"");
            writer.write("\n");
            writer.write(lib.stream().map(libr -> libr.getId() + " " + libr.getContainsBooks().size() + "\n" + libr.getContainsBooks().values().stream().sorted(Comparator.comparingInt(Books::getScore)).map(books34 -> books34.getId() +"").collect(Collectors.joining(" ")) + "\n").collect(Collectors.joining()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double scoreMoyenLibrary(Library library) {
        int scoreTotal = library.getContainsBooks().values().stream().mapToInt(Books::getScore).sum();
        return scoreTotal / (0.0 + library.getSignUpProcess() + (books.size() + 0.0 / library.getCanShipPerDay()));
    }
}
