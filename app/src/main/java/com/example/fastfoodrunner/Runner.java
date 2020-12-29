package com.example.fastfoodrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

class Runner {

    public Bitmap[] runner;
    public int xPosition;
    public int yPosition;
    public int frameNum =0;
    public Rect runnerRectangle;
    public boolean isGoingUp = false;
    private final int YPOS;
    private int width;
    private int height;
    private final float UPVELOCITY = 45;  // Manipulate to change height
    private float downVelocity = 0, upVelocity = UPVELOCITY;
    public float gravityUp = 2.5f;
    private float gravityDown = 1.4f;
    private int delayRunnerMove = 0;


    public Runner(Resources resources, int level){
        runner = new Bitmap[4];
        if(level == 0){
            runner[0] = BitmapFactory.decodeResource(resources, R.drawable.frame1);
            runner[1] = BitmapFactory.decodeResource(resources, R.drawable.frame2);
            runner[2] = BitmapFactory.decodeResource(resources, R.drawable.frame3);
            runner[3] = BitmapFactory.decodeResource(resources, R.drawable.frame4);
        }else{
            runner[0] = BitmapFactory.decodeResource(resources, R.drawable.mcframe1);
            runner[1] = BitmapFactory.decodeResource(resources, R.drawable.mcframe2);
            runner[2] = BitmapFactory.decodeResource(resources, R.drawable.mcframe3);
            runner[3] = BitmapFactory.decodeResource(resources, R.drawable.mcframe4);
        }

        width = runner[0].getWidth() / 4;
        height = runner[0].getHeight() / 4;

        for(int i = 0; i < runner.length; i++){
            runner[i] = Bitmap.createScaledBitmap(runner[i], width, height, false);
        }

        xPosition = Game.getScreenWidth() / 10;
        if(level == 0){

            YPOS = (Game.getScreenHeight() / 2) + 100;
        } else{
            YPOS = (Game.getScreenHeight() / 2) + 136;
        }
        yPosition = YPOS;

    }

    public void jump(){

        if(!isGoingUp){
            downVelocity += gravityDown;
            yPosition += downVelocity;
        }


        if(yPosition > YPOS){
            yPosition = YPOS;
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
            if (frameNum < 3) {
                frameNum++;
            }
            else{
                frameNum = 0;
            }
        }
    }



}
