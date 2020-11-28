package com.example.fastfoodrunner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

class Game extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    Background background1, background2;
    Paint paint;

    public Game(Context context){
        super(context);


        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources());
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources());

        background2.x = getScreenWidth();

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
    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }


    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
