package com.example.fastfoodrunner;

import android.content.Context;
import android.view.SurfaceView;

class Game extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;

    public Game(Context context) {
        super(context);
    }

    @Override
    public void run() {

        while(isPlaying){
            update();
            draw();
        }

    }

    public void resume(){

        thread = new Thread(this);
        thread.start();
        isPlaying = true;
        run();

    }

    public void pause(){

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
