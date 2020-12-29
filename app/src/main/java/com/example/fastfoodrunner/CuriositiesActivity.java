package com.example.fastfoodrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CuriositiesActivity extends Activity {

    private final ArrayList<String> curiosities = new ArrayList<>();
    Random rand = new Random();

    public void gra(View view){

        Intent intent = new Intent(CuriositiesActivity.this, GameActivity.class);
        intent.putExtra("Level", getIntent().getIntExtra("Level", 0));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curiosities);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        InputStream is = this.getResources().openRawResource(R.raw.curiosities);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            String curiosity = "";
            try {

                if ((curiosity = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            curiosities.add(curiosity);
        }



        TextView curiosityText = findViewById(R.id.curiosities);
        int text = rand.nextInt(curiosities.size());
        curiosityText.setText(curiosities.get(text));

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        System.out.println(sessionId);
    }



}