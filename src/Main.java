/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final String A = "data/a_example.txt";
    public static final String B = "data/b_read_on.txt";
    public static final String C = "data/c_incunabula.txt";
    public static final String D = "data/d_tough_choices.txt";
    public static final String E = "data/e_so_many_books.txt";
    public static final String F = "data/f_libraries_of_the_world.txt";

    private static Model loadData(String file) {
        try {
            List<String> collect = Files.lines(Paths.get(file)).collect(Collectors.toList());

            List<Books> books = new ArrayList<>();
            List<Library> libraries = new ArrayList<>();
            int daysForScanning;


            String[] s = collect.get(0).split(" ");
            int nbBooks = Integer.parseInt(s[0]);
            int nbLibrary = Integer.parseInt(s[1]);
            daysForScanning = Integer.parseInt(s[2]);

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

                List<Books> booksInLib =
                        Arrays.stream(booksInLibrary).map(iden -> books.get(Integer.parseInt(iden))).collect(Collectors.toList());

                libraries.add(new Library(libraryId++, booksInLib, Integer.parseInt(libraryStuff[1]),
                        Integer.parseInt(libraryStuff[2])));
            }
            return new Model(libraries, daysForScanning, new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AtomicLong totalScore= new AtomicLong();
        Stream.of(A,B,C,D,E,F).forEach(data -> {
            Model model = loadData(data);

            List<Library> libraries = model.getLibraries();
            int daysForScanning = model.getDaysForScanning();
            Set<Books> alreadySent = model.getAlreadySent();

            System.out.println(data);

            AtomicInteger accuDay = new AtomicInteger(0);
            int i = 0;
            List<Library> result = new ArrayList<>();
            while (accuDay.get() < daysForScanning) {
                if (i++%100 ==0) {
                    System.out.println(accuDay.get());
                }
                Library library = libraries.stream().parallel().max((o1, o2) -> {
                    if (scoreMaxPossibleLibrary(o1, accuDay.get(), daysForScanning,alreadySent) > scoreMaxPossibleLibrary(o2,
                            accuDay.get(), daysForScanning, alreadySent)) {
                        return 1;
                    }
                    return -1;
                }).orElse(null);

                if (library == null) {
                    break;
                }

                accuDay.set(accuDay.get() + library.getSignUpProcess());

                if (accuDay.get() > daysForScanning) {
                    break;
                }

                int daysLeft = daysForScanning - accuDay.get();
                BigInteger nbTotalBooks = BigInteger.valueOf(daysLeft).multiply(BigInteger.valueOf(library.getCanShipPerDay()));

                List<Books> collect =
                        library.getContainsBooks().stream().filter(books -> !alreadySent.contains(books)).sorted(Comparator.comparing(Books::getScore).reversed()).limit(nbTotalBooks.longValue()).collect(Collectors.toList());
                library.setContainsBooks(collect);

                alreadySent.addAll(collect);
                result.add(library);
                libraries.remove(library);
            }

            System.out.println(data);
            System.out.println("accuDay " +accuDay.get());
            System.out.println("result size " +result.size());
            long score = score(result);
            totalScore.addAndGet(score);
            System.out.println("score " + score);
            printResult(result,data);
            System.out.println("-----------------------------");
        });

        System.out.println("total score " + totalScore);



    }

    private static void printResult(List<Library> lib, String path) {
        try {
            List<Library> collect =
                            lib.stream().filter(libr -> !libr.getContainsBooks().isEmpty())
                                            .collect(Collectors.toList());
            FileWriter writer = new FileWriter(new File(path + score(lib) + ".txt"));
            writer.write(collect.size() + "");
            writer.write("\n");
            writer.write(collect.stream().map(libr -> libr.getId() + " " + libr.getContainsBooks().size() + "\n" + libr.getContainsBooks().stream().map(books34 -> books34.getId() +"").collect(Collectors.joining(" "))).collect(Collectors.joining("\n")));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long score(List<Library> libraries) {
        return libraries.stream().flatMap(lib -> lib.getContainsBooks().stream()).mapToInt(Books::getScore).sum();
    }

    private static int scoreMaxPossibleLibrary(Library library, int day, int maxDays, Set<Books> alreadySent) {
//        return library.getSignUpProcess();
        int nbDays = maxDays - (day + library.getSignUpProcess());
        if (nbDays < 0) {
            return 0;
        }
        BigInteger nbTotalBooks = BigInteger.valueOf(nbDays).multiply(BigInteger.valueOf(library.getCanShipPerDay()));

        return library.getContainsBooks().stream().filter(books -> !alreadySent.contains(books)).sorted(Comparator.comparing(Books::getScore).reversed()).limit(nbTotalBooks.longValue()).mapToInt(Books::getScore).sum();
    }
}
