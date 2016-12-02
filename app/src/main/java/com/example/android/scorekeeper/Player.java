package com.example.android.scorekeeper;


public class Player {
    private final int MAX_GAMES = 7;
    private final int MAX_POINTS = 4;

    private String name;

    private int[] matchRes;
    private int set;
    private int gamePoints;
    private int wonSets;

    private boolean advantage;

    public Player(String name) {
        this.name = name;
        matchRes = new int[MAX_GAMES];
        set = 0;
        gamePoints = 0;
        wonSets = 0;
        advantage = false;
    }

    public int[] getMatchRes() {return matchRes;}
    public int getSet(){return set;}
    public int getSetValue() {return matchRes[set];}
    public int getPoints() {
        return gamePoints;
    }
    public boolean getAdvantage() {return advantage;}
    public int getWinSets() {return wonSets;}
    public String getName(){return name;}

    public void setAdvantage(boolean value) {this.advantage = value;}


    public void incPoints() {
        gamePoints++;
        advantage = true;
        if (gamePoints >= MAX_POINTS) {
            matchRes[set]++;
            gamePoints = 0;
        }
    }

    public void looseGame() {
        gamePoints = 0;
    }

    public void nextSet(boolean win) {
        if (win) wonSets++;
        set++;
    }
    public void decSets(){
        set--;
    }

    public void reset() {
        matchRes = new int[MAX_GAMES];
        set = 0;
        gamePoints = 0;
        advantage = false;
        wonSets = 0;
    }
}