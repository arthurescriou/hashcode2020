/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    private int id;
    private Map<Integer,Books> containsBooks;
    private int signUpProcess;
    private int canShipPerDay;

    public Library(int id, Map<Integer, Books> containsBooks, int signUpProcess, int canShipPerDay) {
        this.id = id;
        this.containsBooks = containsBooks;
        this.signUpProcess = signUpProcess;
        this.canShipPerDay = canShipPerDay;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", containsBooks=" + containsBooks.entrySet().stream().map(entry -> entry.getKey()+" " +entry.getValue().toString()).collect(Collectors.joining("{",",","}")) +
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

    public Map<Integer, Books> getContainsBooks() {
        return containsBooks;
    }

    public void setContainsBooks(Map<Integer, Books> containsBooks) {
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
