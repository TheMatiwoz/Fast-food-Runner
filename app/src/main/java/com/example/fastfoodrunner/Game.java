package com.example.fastfoodrunner;

import android.annotation.SuppressLint;
import android.content.Intent;
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

/**
 * Main class, all necessary objects are created here
 *
 */
class Game extends SurfaceView implements Runnable {


    private final ArrayList<JunkFood> junkFood = new ArrayList<>();
    private final Background background1;
    private final Background background2;
    private final Runner runner;
    private final Paint paint;
    private final HighScore score;
    private final ArrayList<Hearts> hearts = new ArrayList<>();
    private final ArrayList<HealthyFood> healthyFoods = new ArrayList<>();
    private final Bitmap gameOver;
    private final MusicPlayer musicPlayer;
    private final Random rand = new Random();
    public GameActivity activity;
    private Thread thread;
    private boolean isPlaying;
    private int junkFoodXPos = 2;
    private int healthyFoodXPos = 2;
    private int minusHeart = 0;
    private boolean firstClick = true;


    public Game(GameActivity activity) {
        super(activity);
        this.activity = activity;

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
        HealthyFood healthyFood4 = new HealthyFood(getResources(), level);
        HealthyFood healthyFood5 = new HealthyFood(getResources(), level);
        healthyFoods.add(healthyFood);
        healthyFoods.add(healthyFood2);
        healthyFoods.add(healthyFood3);
        healthyFoods.add(healthyFood4);
        healthyFoods.add(healthyFood5);

        JunkFood junkFood1 = new JunkFood(getResources(), level);
        JunkFood junkFood2 = new JunkFood(getResources(), level);
        JunkFood junkFood3 = new JunkFood(getResources(), level);
        JunkFood junkFood4 = new JunkFood(getResources(), level);
        JunkFood junkFood5 = new JunkFood(getResources(), level);
        junkFood.add(junkFood1);
        junkFood.add(junkFood2);
        junkFood.add(junkFood3);
        junkFood.add(junkFood4);
        junkFood.add(junkFood5);


        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.koniec);

        score = new HighScore(activity, level);

        for (HealthyFood i : healthyFoods) {
            initialHealthyFoodPosition(i);
        }

        for (JunkFood i : junkFood) {
            initialJunkFoodPosition(i);
        }

        paint = new Paint();
        paint.setTextSize(64);
        paint.setFakeBoldText(true);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public void run() {

        while (isPlaying) {
            update();
            draw();
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for changing the position of objects on the screen
     */
    private void update() {

        background1.backgroundChange();
        background2.backgroundChange();
        runner.jump();

        changeHealthyFoodPosition();
        changeJunkFoodPosition();

        runner.updateRectPosition();
        runner.delayMove();
    }

    /**
     * Responsible for drawing objects on the screen
     */
    private void draw() {

        if (getHolder().getSurface().isValid()) {

            // draw background
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            // draw string before first click
            if (firstClick) {
                canvas.drawText("Kliknij aby rozpocząć", (getScreenWidth() / 2f - 300f), getScreenHeight() / 2f, paint);
            }

            // draw junk food obstacles
            for (JunkFood food : junkFood) {
                if (!Rect.intersects(food.rectangle, runner.runnerRectangle) && !food.isCollision) { // draw junk food bitmap when there is no collision
                    canvas.drawBitmap(food.junkFood, food.x, food.y, paint);
                }
                if (Rect.intersects(runner.runnerRectangle, food.rectangle)) { // do not draw junk food bitmap when there is collision
                    food.isCollision = true;
                    if (food.firstCollision && !firstClick) {
                        musicPlayer.collisionSound();
                        food.firstCollision = false;
                        minusHeart++;
                    }
                }
            }

            // draw healthy food bitmap
            for (HealthyFood he : healthyFoods) {
                if (!Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle) && !he.isCollision) {
                    canvas.drawBitmap(he.healthyFood, he.x, HealthyFood.y, paint);
                }
                if (Rect.intersects(runner.runnerRectangle, he.healthyFoodRectangle)) {
                    he.isCollision = true;
                    if (he.firstCollision && !firstClick) {
                        musicPlayer.pointSound();
                        score.gamePoints++;
                        he.firstCollision = false;
                    }
                }
            }

            // draw runner
            canvas.drawBitmap(runner.runner[runner.frameNum], runner.xPosition, runner.yPosition, paint);

            // draw proper number of hearts
            for (int i = 0; i < hearts.size() - minusHeart; i++) {
                canvas.drawBitmap(hearts.get(i).heart, (hearts.get(i).x + 15) + i * hearts.get(i).width, hearts.get(i).y, paint);
            }

            // draw points in rhe right corner of the screen
            canvas.drawText(score.points(), score.x, score.y, paint);

            for (JunkFood i : junkFood) {
                if (minusHeart >= 3) {
                    canvas.drawBitmap(i.junkFood, i.x, i.y, paint);
                    canvas.drawBitmap(gameOver, (getScreenWidth() / 2f - gameOver.getWidth() / 2f + 50), (getScreenHeight() / 2f - gameOver.getHeight() / 2f), paint);
                }
            }

            getHolder().unlockCanvasAndPost(canvas);

            if (minusHeart >= 3) {
                isPlaying = false;
            }
        }
    }

    private void backToMainActivity() {

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();

    }

    public void changeJunkFoodPosition() {
        if (junkFoodXPos > (junkFood.size() + 2)) { //
            junkFoodXPos = 2; // a multiplier that determines the spacing between next objects
        }
        for (JunkFood i : junkFood) {
            if (!firstClick) {
                i.junkFoodChange(); // start changing object position after first click
            }
            if (i.x + i.width < 0) {
                initialJunkFoodPosition(i);
                if (score.gamePoints > 15 * i.multiply) {
                    i.changeSpeed(5); // after multiple 15 points increase movement of junk food objects
                    i.multiply++;
                }
                i.isCollision = false;
                i.firstCollision = true;
            }
            i.updateRectPosition();
        }
    }

    public void changeHealthyFoodPosition() {
        if (healthyFoodXPos > (healthyFoods.size() + 2)) {
            healthyFoodXPos = 2; // a multiplier that determines the spacing between next objects
        }
        for (HealthyFood i : healthyFoods) {
            if (!firstClick) {
                i.changePosition(); // start changing object position after first click
            }
            if (i.x + i.width < 0 || i.isCollision) {
                initialHealthyFoodPosition(i);
                i.isCollision = false;
                i.firstCollision = true;
            }
            i.updateRectPosition();
        }
    }

    /**
     * Set new HealthyFood object position on right side of screen
     * @param food HealthyFood bitmap which should be changed
     */
    private void initialHealthyFoodPosition(HealthyFood food){
        food.x = Game.getScreenWidth() + (rand.nextInt(100) + 500) * healthyFoodXPos;
        healthyFoodXPos++;
    }

    /**
     * Set new JunkFood object position on right side of screen
     * @param food JunkFood bitmap which should be changed
     */
    private void initialJunkFoodPosition(JunkFood food){
        food.x = Game.getScreenWidth() + (rand.nextInt(100) + 600) * junkFoodXPos;
        junkFoodXPos++;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isPlaying && event.getAction() == MotionEvent.ACTION_DOWN) {
            musicPlayer.stop();
            score.saveIfHighScore();
            backToMainActivity();
        }


        if ((event.getAction() == MotionEvent.ACTION_DOWN) && (runner.yPosition >= ((Game.getScreenHeight() / 2) + 100) || runner.clickCounter < 1)) {

            if (firstClick) {

                firstClick = false;
            }
            runner.clickCounter++;
            runner.isGoingUp = true;
        }
        return true;
    }
}
