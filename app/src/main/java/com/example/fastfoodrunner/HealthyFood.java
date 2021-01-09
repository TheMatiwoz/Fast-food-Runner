package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Create bitmap of healthy food, scale it and change position
 */
class HealthyFood {
    static int y = Game.getScreenHeight() / 2 + 220;
    public int x;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int width;
    int height;
    Bitmap healthyFood;
    Rect healthyFoodRectangle;

    HealthyFood(Resources resources, int level) {
        int[] healthyFoodList = new int[2];
        healthyFoodList[0] = R.drawable.orange;
        healthyFoodList[1] = R.drawable.tomato;

        healthyFood = BitmapFactory.decodeResource(resources, healthyFoodList[level]);
        width = Game.getScreenWidth() / 8;
        height = Game.getScreenHeight() / 7;
        healthyFood = Bitmap.createScaledBitmap(healthyFood, width, height, false);
    }

    public void changePosition() {
        x -= 15;
    }

    /**
     * Method updates position of rectangle which is around healthy food bitmap. Based on these coordinates program knows about collisions.
     */
    public void updateRectPosition() {
        healthyFoodRectangle = new Rect(x, y - height, x + width, y);
    }

}
