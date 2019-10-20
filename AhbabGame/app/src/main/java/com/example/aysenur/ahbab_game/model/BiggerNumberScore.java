package com.example.aysenur.ahbab_game.model;

public class BiggerNumberScore {

    private int score;
    private int time;
    private String id;
    private String date;

    public BiggerNumberScore() {
    }

    public BiggerNumberScore(int score, int time, String id, String date) {
        this.score = score;
        this.time = time;
        this.id = id;
        this.date = date;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getTime() { return time; }

    public void setTime(int time) { this.time = time; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
