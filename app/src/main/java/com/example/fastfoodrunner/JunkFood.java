package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

class JunkFood {
    private final int level;
    public int x = Game.getScreenWidth() + 700*4;
    public boolean isCollision = false;
    public boolean firstCollision = true;
    int y = Game.getScreenHeight() / 2 + 240;
    int width;
    int height;
    Bitmap junkFood;
    Rect rectangle;

    JunkFood(Resources resources, int level){

        this.level = level;

        int[] junkFoodList = new int[2];
        junkFoodList[0] = R.drawable.kfcbox;
        junkFoodList[1] = R.drawable.burger;

        junkFood = BitmapFactory.decodeResource(resources, junkFoodList[level]);
        width = Game.getScreenWidth() / 10;
        height = Game.getScreenHeight() / 7;
        junkFood = Bitmap.createScaledBitmap(junkFood, width, height, false);


    }

    public void junkFoodChange(){

        if(level == 0){
            x -= 15;
        }else{
            x -=17;
        }

    }

    public void updateRectPosition(){
         rectangle = new Rect(x,y - height, x + width, y);

    }
}
