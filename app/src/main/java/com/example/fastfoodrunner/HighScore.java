package com.example.fastfoodrunner;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Keeps variable with current game points and has fields to keep high scores on each level
 */
class HighScore {
    private final SharedPreferences KFCPrefs;
    private final SharedPreferences MCPrefs;
    private final int level;
    int gamePoints = 0;
    int x = Game.getScreenWidth() - 250;
    int y = 100;

    public HighScore(GameActivity activity, int level) {
        this.level = level;
        KFCPrefs = activity.getSharedPreferences("kfc_game", Context.MODE_PRIVATE);
        MCPrefs = activity.getSharedPreferences("mc_game", Context.MODE_PRIVATE);
    }

    public String points() {
        return "WYNIK: " + gamePoints;
    }

    public void saveIfHighScore() {

        if (KFCPrefs.getInt("highscore", 0) < gamePoints && level == 0) {
            SharedPreferences.Editor editor = KFCPrefs.edit();
            editor.putInt("highscore", gamePoints);
            editor.apply();
        }
        if (MCPrefs.getInt("highscore", 0) < gamePoints && level == 1) {
            SharedPreferences.Editor editor = MCPrefs.edit();
            editor.putInt("highscore", gamePoints);
            editor.apply();
        }

    }
}
