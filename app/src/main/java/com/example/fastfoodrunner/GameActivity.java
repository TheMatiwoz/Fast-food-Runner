package com.example.fastfoodrunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Starts and pause the game
 */
public class GameActivity extends Activity {

    Game game;

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);


        game = new Game(this);
        setContentView(game);


    }
}