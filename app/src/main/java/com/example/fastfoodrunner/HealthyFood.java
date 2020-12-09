package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class HealthyFood {
    public int x = 0 ;
    int y = (int) Game.getScreenHeight() / 2 + 200;
    int width;
    int height;
    Bitmap healthyFood;

    HealthyFood(Resources resources){

        healthyFood = BitmapFactory.decodeResource(resources, R.drawable.broccoli);
        width =(int) Game.getScreenWidth() / 6;
        height =(int) Game.getScreenHeight() / 6;
        healthyFood = Bitmap.createScaledBitmap(healthyFood, width, height, false);

    }

    public void healthyFoodChange(){

        x -= 10;
    }
}
