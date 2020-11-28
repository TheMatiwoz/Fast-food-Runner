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
}
