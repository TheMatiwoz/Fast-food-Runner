package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Class JunkFood create bitmap of junk food, scale it and change position
 */
class JunkFood {
    public int x;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    public int multiply = 1;
    int y = Game.getScreenHeight() / 2 + 240;
    int width;
    int height;
    Bitmap junkFood;
    Rect rectangle;
    private int speed = 15;

    JunkFood(Resources resources, int level) {

        int[] junkFoodList = new int[2];
        junkFoodList[0] = R.drawable.kfcbox;
        junkFoodList[1] = R.drawable.burger;

        junkFood = BitmapFactory.decodeResource(resources, junkFoodList[level]);
        width = Game.getScreenWidth() / 10;
        height = Game.getScreenHeight() / 7;
        junkFood = Bitmap.createScaledBitmap(junkFood, width, height, false);


    }

    public void changeSpeed(int newSpeed) {
        speed += newSpeed;
    }

    public void junkFoodChange() {

        x -= speed;

    }

    /**
     * Method updates position of rectangle which is around junk food bitmap. Based on these coordinates program knows about collisions.
     */
    public void updateRectPosition() {
        rectangle = new Rect(x, y - height, x + width, y);

    }
}
