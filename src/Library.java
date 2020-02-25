/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    private int id;
    private List<Books> containsBooks;
    private int signUpProcess;
    private int canShipPerDay;
    private int startingDate;

    public int getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(int startingDate) {
        this.startingDate = startingDate;
    }

    public Library(int id, List<Books> containsBooks, int signUpProcess, int canShipPerDay) {
        this.id = id;
        this.containsBooks = containsBooks;
        this.signUpProcess = signUpProcess;
        this.canShipPerDay = canShipPerDay;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", containsBooks=" + containsBooks.stream().map(Books::toString).collect(Collectors.joining("{",",","}")) +
                ", signUpProcess=" + signUpProcess +
                ", canShipPerDay=" + canShipPerDay +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Books> getContainsBooks() {
        return containsBooks;
    }

    public void setContainsBooks(List<Books> containsBooks) {
        this.containsBooks = containsBooks;
    }

    public int getSignUpProcess() {
        return signUpProcess;
    }

    public void setSignUpProcess(int signUpProcess) {
        this.signUpProcess = signUpProcess;
    }

    public int getCanShipPerDay() {
        return canShipPerDay;
    }

    public void setCanShipPerDay(int canShipPerDay) {
        this.canShipPerDay = canShipPerDay;
    }
}
