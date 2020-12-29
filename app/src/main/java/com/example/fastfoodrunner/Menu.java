package com.example.fastfoodrunner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends Activity {

    public void startKFC(View view){
        Intent intent = new Intent(Menu.this, CuriositiesActivity.class);
        intent.putExtra("Level", 0);
        startActivity(intent);

    }

    public void startMC(View view){
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

        TextView KFChighScoreTxt = findViewById(R.id.KFCHighscore);

        final SharedPreferences KFCprefs = getSharedPreferences("kfc_game", MODE_PRIVATE);
        KFChighScoreTxt.setText("HighScore: " + KFCprefs.getInt("highscore", 0));

        TextView MChighScoreTxt = findViewById(R.id.MCHighscore);

        final SharedPreferences MCprefs = getSharedPreferences("mc_game", MODE_PRIVATE);
        MChighScoreTxt.setText("HighScore: " + MCprefs.getInt("highscore", 0));
    }


}