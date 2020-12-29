package com.example.fastfoodrunner;

import android.annotation.SuppressLint;
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

    //TODO
    // generowanie healthyfood i junkfood
    // scalowanie na inne użądzenia


    public static float scrRatioX, scrRatioY;
    private final ArrayList<JunkFood> junkFood = new ArrayList<>();
    private Thread thread;
    private boolean isPlaying;
    private final Background background1;
    private final Background background2;
    private final Runner runner;
    private final Paint paint;
    private final HighScore score;
    private final ArrayList<Hearts> hearts = new ArrayList<>();
    private final ArrayList<HealthyFood> healthyFoods = new ArrayList<>();
    private int junkFoodxpos = 2;
    private int healthyFoodXPos = 2;
    private Random rand = new Random();
    private int minusHeart = 0;
    public GameActivity activity;
    private final Bitmap gameOver;
    private final MusicPlayer musicPlayer;
    private boolean firstClick = true;
    private int screenX;
    private int screenY;

    public Game(GameActivity activity){
        super(activity);
        this.activity = activity;

        screenX = getScreenWidth();
        screenY = getScreenHeight();
        scrRatioX = 1794f / screenX;
        scrRatioY = 1080f / screenY;

        int level = activity.getIntent().getIntExtra("Level", 0);

        musicPlayer = new MusicPlayer(activity, level);
        musicPlayer.play();

        background1 = new Background(getScreenWidth(), getScreenHeight(), getResources(), level);
        background2 = new Background(getScreenWidth(), getScreenHeight(), getResources(), level);
        background2.x = getScreenWidth();

        Hearts heart1 = new Hearts(getResources());
        Hearts heart2 = new Hearts(getResources());
        Hearts heart3 = new Hearts(getResources());
        hearts.add(heart1);
        hearts.add(heart2);
        hearts.add(heart3);

        runner = new Runner(getResources(), level);

        HealthyFood healthyFood = new HealthyFood(getResources(), level);
        HealthyFood healthyFood2 = new HealthyFood(getResources(), level);
        HealthyFood healthyFood3 = new HealthyFood(getResources(), level);
        healthyFoods.add(healthyFood);
        healthyFoods.add(healthyFood2);
        healthyFoods.add(healthyFood3);

        JunkFood junkFood1 = new JunkFood(getResources(), level);
        JunkFood junkFood2 = new JunkFood(getResources(), level);
        JunkFood junkFood3 = new JunkFood(getResources(), level);

        junkFood.add(junkFood1);
        junkFood.add(junkFood2);
        junkFood.add(junkFood3);

        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.koniec);

        score = new HighScore(activity, level);

        for (HealthyFood i:healthyFoods) {
            i.x =  Game.getScreenWidth() + 500*healthyFoodXPos;
            healthyFoodXPos ++;
        }

        for (JunkFood i: junkFood) {
            i.x =  Game.getScreenWidth() + 600*junkFoodxpos;
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
        defaultJunkFoodPosition();

        runner.updateRectPosition();
        runner.delayMove();


    }

    private void draw(){

        if(getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);


            for(JunkFood food : junkFood){
                if(!Rect.intersects(food.rectangle, runner.runnerRectangle) && !food.isCollision){
                    canvas.drawBitmap(food.junkFood, food.x, food.y, paint);
                }
                if(Rect.intersects(runner.runnerRectangle, food.rectangle)) {
                    food.isCollision = true;
                    if (food.firstCollision && !firstClick) {
                        musicPlayer.collisionSound();
                        food.firstCollision = false;
                        minusHeart++;
                    }
                }
            }

            for(HealthyFood he:healthyFoods){
                if(!Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle) && !he.isCollision){
                    canvas.drawBitmap(he.healthyFood, he.x, he.y, paint);
                }
                if(Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle)){
                    he.isCollision = true;
                    if(he.firstCollision && !firstClick){
                        musicPlayer.pointSound();
                        score.gamePoints ++;
                        he.firstCollision = false;
                    }

                }
            }

            canvas.drawBitmap(runner.runner[runner.frameNum], runner.xPosition, runner.yPosition, paint);

            for(int i = 0; i<hearts.size() - minusHeart; i++){
                canvas.drawBitmap(hearts.get(i).heart, hearts.get(i).x + 15 +i*hearts.get(i).width, hearts.get(i).y, paint);
            }

            canvas.drawText(score.points(), score.x, score.y, paint);

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

    private void backToMainActivity() {

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();

    }

    public void defaultJunkFoodPosition(){
        if(junkFoodxpos>(junkFood.size()+2)){
            junkFoodxpos = 2;
        }
        for(JunkFood i : junkFood){
            i.junkFoodChange();
            if(i.x + i.width < 0){
                i.x = Game.getScreenWidth() + 600*junkFoodxpos;
                junkFoodxpos ++;
                i.isCollision = false;
                i.firstCollision = true;
            }
            i.updateRectPosition();
        }
    }

    public void defaultHealthyFoodPosition(){
        if(healthyFoodXPos > (healthyFoods.size() + 2)){
            healthyFoodXPos = 2;
        }
        for(HealthyFood i : healthyFoods){
            i.healthyFoodChange();
            if(i.x + i.width < 0 || i.isCollision){
                i.x = Game.getScreenWidth() + 500*healthyFoodXPos;
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
            musicPlayer.stop();
            score.saveIfHighScore();
            backToMainActivity();
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN && runner.yPosition >= ((Game.getScreenHeight() / 2) + 100)) {

            if(firstClick){

                firstClick = false;
            }
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
