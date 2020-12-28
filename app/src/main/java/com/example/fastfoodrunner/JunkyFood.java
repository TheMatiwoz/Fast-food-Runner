package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

class JunkyFood {
    public int x = Game.getScreenWidth() + 700*4;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int y = Game.getScreenHeight() / 2 + 240;
    int width;
    int height;
    Bitmap junkyFood;
    Rect rectangle;

    JunkyFood(Resources resources){

        junkyFood = BitmapFactory.decodeResource(resources, R.drawable.kfcbox);
        width = Game.getScreenWidth() / 10;
        height = Game.getScreenHeight() / 7;
        junkyFood = Bitmap.createScaledBitmap(junkyFood, width, height, false);


    }

    public void junkyFoodChange(){

        x -= 15;
    }

    public void updateRectPosition(){
         rectangle = new Rect(x,y - height, x + width, y);

    }
}
