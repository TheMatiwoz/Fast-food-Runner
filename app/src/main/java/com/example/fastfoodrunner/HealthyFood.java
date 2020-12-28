package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


class HealthyFood {
    public int x = 0 ;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int y = Game.getScreenHeight() / 2 + 220;
    int width;
    int height;
    Bitmap healthyFood;
    Rect healthyFoodRectangle;

    HealthyFood(Resources resources){

        healthyFood = BitmapFactory.decodeResource(resources, R.drawable.onion);
        width = Game.getScreenWidth() / 8;
        height = Game.getScreenHeight() / 7;
        healthyFood = Bitmap.createScaledBitmap(healthyFood, width, height, false);


    }

    public void healthyFoodChange(){

        x -= 15;
    }

    public void updateRectPosition(){
        healthyFoodRectangle = new Rect(x,y - height, x + width, y);

    }

}
