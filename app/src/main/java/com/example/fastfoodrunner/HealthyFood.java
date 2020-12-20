package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


class HealthyFood {
    public int x = 0 ;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int y = Game.getScreenHeight() / 2 + 200;
    int width;
    int height;
    Bitmap healthyFood;
    Rect healthyFoodRectangle;

    HealthyFood(Resources resources){

        healthyFood = BitmapFactory.decodeResource(resources, R.drawable.onion);
        width = Game.getScreenWidth() / 7;
        height = Game.getScreenHeight() / 6;
        healthyFood = Bitmap.createScaledBitmap(healthyFood, width, height, false);


    }

    public void healthyFoodChange(){

        x -= 10;
    }

    public void updateRectPosition(){
        healthyFoodRectangle = new Rect(x,y - height, x + width, y);

    }

}
