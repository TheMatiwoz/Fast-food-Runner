package com.example.fastfoodrunner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Activity where you can choose level
 */
public class Menu extends Activity {

    public void startKFC(View view) {
        Intent intent = new Intent(Menu.this, CuriositiesActivity.class);
        intent.putExtra("Level", 0);
        startActivity(intent);

    }

    public void startMC(View view) {
        Intent intent = new Intent(Menu.this, CuriositiesActivity.class);
        intent.putExtra("Level", 1);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        TextView KFCHighScoreTxt = findViewById(R.id.KFCHighscore);

        final SharedPreferences KFCPrefs = getSharedPreferences("kfc_game", MODE_PRIVATE);
        KFCHighScoreTxt.setText("HighScore: " + KFCPrefs.getInt("highscore", 0)); // Responsible for showing current high score on KFC level

        TextView MCHighScoreTxt = findViewById(R.id.MCHighscore);

        final SharedPreferences MCPrefs = getSharedPreferences("mc_game", MODE_PRIVATE);
        MCHighScoreTxt.setText("HighScore: " + MCPrefs.getInt("highscore", 0)); // Responsible for showing current high score on MCDonald level
    }

}