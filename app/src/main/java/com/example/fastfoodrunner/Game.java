package com.example.fastfoodrunner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

class Game extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1;
    private Background background2;
    private Runner runner;
    private Paint paint;
    private int frameNum =0;
    private int pause= 0;
    float v = 0;
    float g = 0.2f;

    public Game(Context context){
        super(context);


        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources());
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources());

        background2.x = getScreenWidth();

        runner = new Runner(getResources());

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

        background1.x -= 5;
        background2.x -= 5;

        if(background1.x + getScreenWidth() < 0){
            background1.x = getScreenWidth();
        }

        if(background2.x + getScreenWidth() < 0){
            background2.x = getScreenWidth();
        }

        // ZA SZYBKO SPADA I SKACZE
        v += g;
        runner.yPosition += v;


        if(runner.yPosition > ((Game.getScreenHeight() / 2) + 100)){
            runner.yPosition = ((Game.getScreenHeight() / 2) + 100);
        }

        if(runner.isGoingUp){
            runner.yPosition -= 500;
            runner.isGoingUp = false;
        }

    }

    private void draw(){
        if(pause < 8){
            pause++;
        }
        else{
            pause = 0;
            if (frameNum < 4) {
                frameNum++;
            }
            else{
                frameNum = 0;
            }
        }



        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            canvas.drawBitmap(runner.runner[frameNum], runner.xPosition, runner.yPosition, paint);
            getHolder().unlockCanvasAndPost(canvas);


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
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
