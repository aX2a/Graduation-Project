package com.example.aysenur.ahbab_game.model;

public class WordGameScore {

    private int timer;
    private int clickNum;
    private String date;

    public WordGameScore() {
    }

    public WordGameScore(int timer, int clickNum, String date) {
        this.timer = timer;
        this.clickNum = clickNum;
        this.date = date;
    }

    public int getTimer() { return timer; }

    public void setTimer(int timer) { this.timer = timer; }

    public int getClickNum() { return clickNum; }

    public void setClickNum(int clickNum) { this.clickNum = clickNum; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
