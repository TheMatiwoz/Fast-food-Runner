package com.example.fastfoodrunner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

class Game extends SurfaceView implements Runnable {

    private final JunkFood junkFood1;
    private final JunkFood junkFood2;
    private final JunkFood junkFood3;
    private ArrayList<JunkFood> junkFood = new ArrayList<>();
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
    private int junkFoodxpos = 2;
    private int healthyFoodXPos = 2;
    private Random rand = new Random();
    private int minusHeart = 0;

    public GameActivity activity;

    private SharedPreferences KFCPrefs;
    private SharedPreferences MCPrefs;

    Bitmap gameOver;


    //TO Do
    // Multiply HealthyFood
    // Improve jumping


    public Game(GameActivity activity){
        super(activity);
        this.activity = activity;

        int level = activity.getIntent().getIntExtra("Level", 0);


        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources(), level);
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources(), level);

        background2.x = getScreenWidth();

        heart1 = new Hearts(getResources());
        heart2 = new Hearts(getResources());
        heart3 = new Hearts(getResources());
        hearts.add(heart1);
        hearts.add(heart2);
        hearts.add(heart3);


        runner = new Runner(getResources(), level);

        healthyFood = new HealthyFood(getResources(), level);
        healthyFood2 = new HealthyFood(getResources(), level);
        healthyFood3 = new HealthyFood(getResources(), level);
        healthyFoods.add(healthyFood);
        healthyFoods.add(healthyFood2);
        healthyFoods.add(healthyFood3);

        junkFood1 = new JunkFood(getResources(), level);
        junkFood2 = new JunkFood(getResources(), level);
        junkFood3 = new JunkFood(getResources(), level);


        junkFood.add(junkFood1);
        junkFood.add(junkFood2);
        junkFood.add(junkFood3);

        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.koniec);


        score = new HighScore(activity, level);

        for (HealthyFood i:healthyFoods) {
            i.x =  Game.getScreenWidth() + 700*healthyFoodXPos;
            healthyFoodXPos ++;
        }

        for (JunkFood i: junkFood) {
            i.x =  Game.getScreenWidth() + 800*junkFoodxpos;
            junkFoodxpos ++;
        }

        paint = new Paint();
        paint.setTextSize(64);
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
        defaultHealthyFoodPosition();

        runner.updateRectPosition();
        defaultJunkFoodPosition();



        runner.delayMove();


    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            for(JunkFood i : junkFood){
                if(!Rect.intersects(i.rectangle, runner.runnerRectangle) && !i.isCollision){
                    canvas.drawBitmap(i.junkFood, i.x, i.y, paint);
                }
                if(Rect.intersects(runner.runnerRectangle, i.rectangle)) {
                    i.isCollision = true;
                    if (i.firstCollision) {
                        i.firstCollision = false;
                        minusHeart++;
                    }
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
            for(JunkFood i : junkFood){
                if(minusHeart>= 3){
                    canvas.drawBitmap(i.junkFood, i.x, i.y, paint);
                    canvas.drawBitmap(gameOver, (getScreenWidth()/2 - gameOver.getWidth()/2 + 50), (getScreenHeight()/2 - gameOver.getHeight()/2), paint);
                }
            }

            getHolder().unlockCanvasAndPost(canvas);

            if(minusHeart >= 3){

                isPlaying = false;
            }


        }


    }

    private void waitBeforeExiting() {

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();

    }

    public void defaultJunkFoodPosition(){
        junkFoodxpos = 2;
        for(JunkFood i : junkFood){
            i.junkFoodChange();
            if(i.x + i.width < 0){
                i.x = Game.getScreenWidth() + 900*junkFoodxpos;
                junkFoodxpos ++;
                i.isCollision = false;
                i.firstCollision = true;
            }
            i.updateRectPosition();
        }
    }

    public void defaultHealthyFoodPosition(){
        healthyFoodXPos = 2;
        for(HealthyFood i : healthyFoods){
            i.healthyFoodChange();
            if(i.x + i.width < 0 || i.isCollision){
                i.x = Game.getScreenWidth() + 900*healthyFoodXPos;
                healthyFoodXPos ++;
                i.isCollision = false;
                i.firstCollision = true;
            }
            i.updateRectPosition();
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(!isPlaying && event.getAction() == MotionEvent.ACTION_DOWN){
            score.saveIfHighScore();
            waitBeforeExiting();
        }

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
