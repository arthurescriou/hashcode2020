/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.List;
import java.util.Set;

public class Model {

    private List<Library> libraries;
    private int DaysForScanning;
    private Set<Books> alreadySent;

    public Model(List<Library> libraries, int daysForScanning, Set<Books> alreadySent) {
        this.libraries = libraries;
        DaysForScanning = daysForScanning;
        this.alreadySent = alreadySent;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public int getDaysForScanning() {
        return DaysForScanning;
    }

    public Set<Books> getAlreadySent() {
        return alreadySent;
    }
}
