package com.example.fastfoodrunner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.constraintlayout.solver.widgets.Rectangle;

class Game extends SurfaceView implements Runnable {

    private final JunkyFood junkyFood;
    private Thread thread;
    private boolean isPlaying;
    private final Background background1;
    private final Background background2;
    private final Runner runner;
    private final Paint paint;
    private Hearts heart1, heart2, heart3;
    private HighScore score;
    private HealthyFood healthyFood, healthyFood2, healthyFood3;
    private ArrayList<Hearts> hearts = new ArrayList<>();
    private boolean firstClick = true;
    private ArrayList<HealthyFood> healthyFoods = new ArrayList<>();
    private int xpos = 2;
    private Random rand = new Random();
    private int minusHeart = 0;


    //TO Do
    // Multiply HealthyFood
    // Improve jumping


    public Game(Context context){
        super(context);


        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources());
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources());

        background2.x = getScreenWidth();

        heart1 = new Hearts(getResources());
        heart2 = new Hearts(getResources());
        heart3 = new Hearts(getResources());
        hearts.add(heart1);
        hearts.add(heart2);
        hearts.add(heart3);


        runner = new Runner(getResources());
        healthyFood = new HealthyFood(getResources());
        healthyFood2 = new HealthyFood(getResources());
        //healthyFood3 = new HealthyFood(getResources());
        healthyFoods.add(healthyFood);
        healthyFoods.add(healthyFood2);
        //healthyFoods.add(healthyFood3);

        junkyFood = new JunkyFood(getResources());

        score = new HighScore();

        for (HealthyFood i:healthyFoods) {
            i.x =  Game.getScreenWidth() + 700*xpos;
            xpos ++;
        }

        paint = new Paint();
        paint.setTextSize(48);
        paint.setFakeBoldText(true);


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

        background1.backgroundChange();
        background2.backgroundChange();
        runner.jump();
        for(HealthyFood he:healthyFoods){
            he.healthyFoodChange();
            if(he.x + he.width < 0){
                he.x = Game.getScreenWidth();
                he.isCollision = false;
                he.firstCollision = true;

            }
            he.updateRectPosition();
        }


        runner.updateRectPosition();


        junkyFood.junkyFoodChange();
        if(junkyFood.x + junkyFood.width < 0){
            junkyFood.x = getScreenWidth();
            junkyFood.isCollision = false;
            junkyFood.firstCollision = true;
        }
        junkyFood.updateRectPosition();


        runner.delayMove();


    }

    private void draw(){





        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            if(!Rect.intersects(junkyFood.rectangle, runner.runnerRectangle) && !junkyFood.isCollision){
                canvas.drawBitmap(junkyFood.junkyFood, junkyFood.x, junkyFood.y, paint);
            }
            if(Rect.intersects(runner.runnerRectangle, junkyFood.rectangle)) {
                junkyFood.isCollision = true;
                if (junkyFood.firstCollision) {
                    junkyFood.firstCollision = false;
                    minusHeart++;
                }
            }



            for(int i = 0; i<hearts.size() - minusHeart; i++){
                canvas.drawBitmap(hearts.get(i).heart, hearts.get(i).x + 15 +i*hearts.get(i).width, hearts.get(i).y, paint);
            }


            for(HealthyFood he:healthyFoods){
                if(!Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle) && !he.isCollision){
                    canvas.drawBitmap(he.healthyFood, he.x, he.y, paint);
                }
                if(Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle)){
                    he.isCollision = true;
                    if(he.firstCollision){
                        score.gamePoints ++;
                        he.firstCollision = false;
                    }

                }
            }
            canvas.drawText(score.points(), score.x, score.y, paint);
            canvas.drawBitmap(runner.runner[runner.frameNum], runner.xPosition, runner.yPosition, paint);
            if(minusHeart>= 3){
                canvas.drawBitmap(junkyFood.junkyFood, junkyFood.x, junkyFood.y, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);

            if(minusHeart >= 3){
                pause();
            }


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
