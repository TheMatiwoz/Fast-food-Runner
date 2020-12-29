package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Background {

    int x = 0;
    int y = 0;
    Bitmap background;

    Background(int screenX, int screenY, Resources resources, int level){

        int[] backgroundList = new int[2];
        backgroundList[0] = R.drawable.kfcbg;
        backgroundList[1] = R.drawable.mcbg;


        background = BitmapFactory.decodeResource(resources, backgroundList[level]);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }

    public void backgroundChange(){
        x -= 15;

        if(x + Game.getScreenWidth() < 0){
            x = Game.getScreenWidth();
        }
    }
}
