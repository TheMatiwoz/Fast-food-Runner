package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Runner {

    Bitmap[] runner;
    int xPosition;
    int yPosition;
    int width;
    int height;
    int frameNumber = 0;
    boolean isGoingUp = false;


    Runner(Resources resources){
        runner = new Bitmap[5];
        runner[0] = BitmapFactory.decodeResource(resources, R.drawable.frame1);
        runner[1] = BitmapFactory.decodeResource(resources, R.drawable.frame2);
        runner[2] = BitmapFactory.decodeResource(resources, R.drawable.frame3);
        runner[3] = BitmapFactory.decodeResource(resources, R.drawable.frame4);
        runner[4] = BitmapFactory.decodeResource(resources, R.drawable.frame5);

        width = runner[0].getWidth() / 4;
        height = runner[0].getHeight() / 4;

        for(int i = 0; i < 5; i++){
            runner[i] = Bitmap.createScaledBitmap(runner[i], width, height, false);
        }

        xPosition = Game.getScreenWidth() / 10;
        yPosition = (Game.getScreenHeight() / 2) + 100;


    }



}
