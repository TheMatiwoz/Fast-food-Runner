package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Create bitmap of heart, scale this bitmap and has field with heart coordinates
 */
class Hearts {
    public final int width;
    public final int height;
    public float x;
    public int y = 15;
    Bitmap heart;

    Hearts(Resources resources) {
        heart = BitmapFactory.decodeResource(resources, R.drawable.heart);
        width = Game.getScreenWidth() / 11;
        height = Game.getScreenHeight() / 8;
        heart = Bitmap.createScaledBitmap(heart, width, height, false);
    }
}
