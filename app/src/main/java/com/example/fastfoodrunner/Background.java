package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Background {

    int x = 0;
    int y = 0;
    Bitmap background;

    Background(int screenX, int screenY, Resources resources){

        background = BitmapFactory.decodeResource(resources, R.drawable.kfcbg);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }

    public void backgroundChange(){
        x -= 15;

        if(x + Game.getScreenWidth() < 0){
            x = Game.getScreenWidth();
        }
    }
}
