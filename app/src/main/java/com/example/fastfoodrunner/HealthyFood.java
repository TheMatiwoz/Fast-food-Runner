package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


class HealthyFood {
    static int y = Game.getScreenHeight() / 2 + 220;
    public int x;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int width;
    int height;
    Bitmap healthyFood;
    Rect healthyFoodRectangle;
    private int speed = 15;

    HealthyFood(Resources resources, int level) {

        int[] healthyFoodList = new int[2];
        healthyFoodList[0] = R.drawable.orange;
        healthyFoodList[1] = R.drawable.tomato;

        healthyFood = BitmapFactory.decodeResource(resources, healthyFoodList[level]);
        width = Game.getScreenWidth() / 8;
        height = Game.getScreenHeight() / 7;
        healthyFood = Bitmap.createScaledBitmap(healthyFood, width, height, false);


    }

    public void changeSpeed(int randomSpeed) {
        speed = randomSpeed;
    }

    public void healthyFoodChange() {

        x -= speed;
    }

    public void updateRectPosition() {
        healthyFoodRectangle = new Rect(x, y - height, x + width, y);

    }

}
