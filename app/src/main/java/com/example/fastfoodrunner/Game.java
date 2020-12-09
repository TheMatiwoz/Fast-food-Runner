package com.example.fastfoodrunner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

class Game extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private final Background background1;
    private final Background background2;
    private final Runner runner;
    private final Paint paint;
    private  HealthyFood healthyFood, healthyFood2, healthyFood3;
    private boolean firstClick = true;
     private ArrayList<HealthyFood> healthyFoods = new ArrayList<>();


    //TO Do
    // Multiply HealthyFood
    // Improve jumping


    public Game(Context context){
        super(context);


        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources());
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources());

        background2.x = getScreenWidth();

        runner = new Runner(getResources());
        healthyFood = new HealthyFood(getResources());
        healthyFood2 = new HealthyFood(getResources());
        healthyFood3 = new HealthyFood(getResources());
        healthyFoods.add(healthyFood);
        healthyFoods.add(healthyFood2);
        healthyFoods.add(healthyFood3);

        for (HealthyFood i:healthyFoods) {
            i.x =  (int) Game.getScreenWidth() + 500;
        }

        paint = new Paint();


    }

    @Override
    public void run() {

        while(isPlaying){
            update();
            draw();
        }

    }

    public void resume(){

        isPlaying = true;
        thread = new Thread(this);
        thread.start();


    }

    public void pause(){

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update(){
        //System.out.println(healthyFood.x);
        background1.backgroundChange();
        background2.backgroundChange();
        runner.jump();
        healthyFood.healthyFoodChange();
        //healthyFood.x -= 10;
        //int q = 1;
        for (HealthyFood i:healthyFoods) {
            if(i.x + i.width < 0){
                i.x =  (int) Game.getScreenWidth();
            }

        }

    }

    private void draw(){

        // Change runner frame method
        if(runner.delayRunnerMove < 8){
            runner.delayRunnerMove++;
        }
        else{
            runner.delayRunnerMove = 0;
            if (runner.frameNum < 4) {
                runner.frameNum++;
            }
            else{
                runner.frameNum = 0;
            }
        }



        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            for (HealthyFood he :
                    healthyFoods) {
                canvas.drawBitmap(he.healthyFood, he.x, he.y, paint);
            }

            canvas.drawBitmap(runner.runner[runner.frameNum], runner.xPosition, runner.yPosition, paint);
            getHolder().unlockCanvasAndPost(canvas);


        }


    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_DOWN && runner.yPosition >= ((Game.getScreenHeight() / 2) + 100)) {
            runner.isGoingUp = true;
        }
        return true;
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
