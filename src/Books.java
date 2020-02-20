/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

public class Books {
    private int id;
    private int score;

    public Books(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public int getNegScore() {
        return -score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}
