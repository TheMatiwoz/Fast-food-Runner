package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

class Runner {

    Bitmap[] runner;
    int xPosition;
    int yPosition;
    public int width;
    public int height;
    boolean isGoingUp = false;
    public final float UPVELOCITY = 45;  // Manipulate to change height
    float downVelocity = 0, upVelocity = UPVELOCITY;
    float gravityUp = 2.5f;
    float gravityDown = 1.4f;
    int frameNum =0;
    int delayRunnerMove = 0;
    Rect runnerRectangle;


    Runner(Resources resources){
        runner = new Bitmap[5];
        runner[0] = BitmapFactory.decodeResource(resources, R.drawable.frame1);
        runner[1] = BitmapFactory.decodeResource(resources, R.drawable.frame2);
        runner[2] = BitmapFactory.decodeResource(resources, R.drawable.frame3);
        runner[3] = BitmapFactory.decodeResource(resources, R.drawable.frame4);
        runner[4] = BitmapFactory.decodeResource(resources, R.drawable.frame5);

        width = runner[0].getWidth() / 4;
        height = runner[0].getHeight() / 4;

        for(int i = 0; i < 5; i++){
            runner[i] = Bitmap.createScaledBitmap(runner[i], width, height, false);
        }

        xPosition = Game.getScreenWidth() / 10;
        yPosition = (Game.getScreenHeight() / 2) + 100;



    }

    public void jump(){

        if(!isGoingUp){
            downVelocity += gravityDown;
            yPosition += downVelocity;
        }


        if(yPosition > ((Game.getScreenHeight() / 2) + 100)){
            yPosition = ((Game.getScreenHeight() / 2) + 100);
            downVelocity = 0;
        }

        if(isGoingUp){

            upVelocity -= gravityUp;
            yPosition -= upVelocity;

        }
        if(upVelocity <= 0){
            isGoingUp = false;
            upVelocity = UPVELOCITY;
        }
    }

    public void updateRectPosition(){

        runnerRectangle = new Rect(xPosition, yPosition - height, xPosition + width, yPosition);
    }

    public void delayMove(){
        // Change runner frame method
        if(delayRunnerMove < 8){
            delayRunnerMove++;
        }
        else{
            delayRunnerMove = 0;
            if (frameNum < 4) {
                frameNum++;
            }
            else{
                frameNum = 0;
            }
        }
    }



}
