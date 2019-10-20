package com.example.aysenur.ahbab_game.model;

public class Move {

    private String date;
    private String level;
    private int move;
    private int timer;

    public Move(){

    }

    public Move(String date, String level, int move, int timer){
        this.date = date;
        this.level = level;
        this.move = move;
        this.timer = timer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getTimer() { return timer; }

    public void setTimer(int timer) { this.timer = timer; }

}
