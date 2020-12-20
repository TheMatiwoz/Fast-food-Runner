package com.example.fastfoodrunner;

class HighScore {
    int gamePoints = 0;
    String stringGamePoinst;
    int x = Game.getScreenWidth() - 200;
    int y = 100;

    public String points(){
        return "WYNIK: " + gamePoints;
    }
}
