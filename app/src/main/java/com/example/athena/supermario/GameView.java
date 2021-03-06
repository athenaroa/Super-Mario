package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class GameView extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;


    //Class objects
    private Player player;
    private LevelOne levelOne;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    //Background variables
    private int motion;
    private int backPosX;
    private int backFrame;
    private final int backgroundSpeed = 12;
    private int midScreenPos;


    //Mario variables
    private long jumpTimeStart;
    private long jumpTimeMax;
    private float runSpeedPerSecond = 100;
    private int frameCount = 6;
    private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;


    private Rect frameToDraw = new Rect(0, 0, 190, 240);
    private RectF whereToDraw = new RectF(10, 580,
            10 + 190, 240);

    //OnTouch event variables
    private int mActivePointerId = INVALID_POINTER_ID;
    private float mLastTouchX;
    private float mLastTouchY;
    public float touchPosX;
    public float touchPosY;

    //Level variables
    private int level;
    ArrayList<Rect> levelCoins;
    ArrayList<Rect> levelBlocks;
    ArrayList<Rect> levelFlowers;
    ArrayList<Rect> levelMushrooms;
    ArrayList<Rect> levelEnemies;

    private int screenWidth;
    private int screenHeight;

    Bitmap bitmap;
    Bitmap back;
    ArrayList<Bitmap> lives;
    Bitmap heart;


    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initializing player object
        player = new Player(context, screenX,screenY);
        //initializing levelOne object
        levelOne = new LevelOne(context, screenX,screenY);


        lives =  new ArrayList<>();
        levelCoins = new ArrayList<>();
        levelBlocks = new ArrayList<>();
        levelFlowers = new ArrayList<>();
        levelMushrooms = new ArrayList<>();
        levelEnemies = new ArrayList<>();

        //initializing drawing objects
        surfaceHolder = getHolder();

        //initializing variables
        level = 1;
        motion = 0;
        backPosX = 0;
        jumpTimeStart = 0;
        jumpTimeMax = 1000;
        backFrame = 1;
        midScreenPos = screenX/2;
        screenWidth = screenX;
        screenHeight = screenY;

    }

    @Override
    public void run() {
        while (playing) {

            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
            control();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1){
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {

        //update the coordinates of Mario
        player.updateMarioForm(levelOne.updateMarioForm());

        //Updates the Level class with current mario position
        levelOne.update(player.getmanXPos(), player.getmanYPos(), player.getFrameWidth(), player.getFrameHeight(), backFrame);

        //Checking if mario is on-top of a block in the level and updates the player object
        if(levelOne.marioOnBlock()){
            player.setMarioOnBlock(true);
        }
        else{
            player.setMarioOnBlock(false);
        }
        //Checking if mario HIT a block in the level and updates the player object
        if(levelOne.marioHitBlock(player.getFrameWidth(),player.getFrameHeight()))
        {
            player.setMarioHitABlock(true);
        }
        else{
            player.setMarioHitABlock(false);
        }

        //Checking if mario HIT a enemy in the level and updates the player object
        if(levelOne.marioHitEnemy()){
            player.setMarioHitEnemy(true);
        }
        else
        {
            player.setMarioHitEnemy(false);
        }

        //update mario position
        player.updateMarioPos();


        //update coordinates of the background
        //if(motion == 1 && (player.getmanXPos() > (getWidth()* 3)/4) && (player.getDirection() == 1)) {
        if(motion == 1 && (player.getmanXPos() > midScreenPos) && (player.getDirection() == 1)){
            player.changeMarioSpeed(-9);
            backPosX += backgroundSpeed;
            levelOne.updateCoinPos(-backgroundSpeed);
            levelOne.updateBlockPos(-backgroundSpeed);
            levelOne.updateFlowerPos(-backgroundSpeed);
            levelOne.updateMushroomPos(-backgroundSpeed);
            levelOne.updateEnemyPos(-backgroundSpeed);

            if(backPosX > getWidth())
            {
                backPosX = 0;
                backFrame += 1;
            }
        }
        //else if(motion == 1 && (player.getmanXPos() < (getWidth()/4)) && (player.getDirection() == 2)) {
        else if(motion == 1 && (player.getmanXPos() < midScreenPos) && (player.getDirection() == 2)) {
            player.changeMarioSpeed(-9);
            backPosX -= backgroundSpeed;
            levelOne.updateCoinPos(backgroundSpeed);
            levelOne.updateBlockPos(backgroundSpeed);
            levelOne.updateFlowerPos(backgroundSpeed);
            levelOne.updateMushroomPos(backgroundSpeed);
            levelOne.updateEnemyPos(backgroundSpeed);

            if(backPosX < 0- getWidth())
            {
                backPosX = 0;
                backFrame -= 1;
            }
        }
        else{
            player.changeMarioSpeed(1);
        }

    }

    public void manageCurrentFrame() {
        if(motion == 1)
        {
            currentFrame++;

            if (currentFrame >= frameCount)
            {
                currentFrame = 0;
            }
        }
        else //motion = 0 , no motion
        {
            if((player.getDirection() == 1)){
                currentFrame = 2;
            }
            else if ((player.getDirection() == 2))
            {
                currentFrame = 3;
            }
            else
            {
                currentFrame = 2;
            }
        }
        frameToDraw.left = currentFrame * player.getFrameWidth();
        frameToDraw.right = frameToDraw.left + player.getFrameWidth();


    }

    public void setBackground(){
        if(level == 1){
            this.back = levelOne.getbackground();
        }
    }

    public int getBackFrame(){
        return backFrame;
    }


    private void draw() {
        //draw the character to the canvas
        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();

            //Drawing the background
            Rect background = new Rect(0 - backPosX,0,getWidth() - backPosX,getHeight());
            Rect backgroundrepeatright = new Rect((getWidth() - backPosX),0,(getWidth() * 2) - backPosX,getHeight());
            Rect backgroundrepeatleft = new Rect((0 - getWidth() - backPosX),0, 0 - backPosX,getHeight());
            setBackground();
            canvas.drawBitmap(back,null,background,null);
            canvas.drawBitmap(back,null,backgroundrepeatright, null);
            canvas.drawBitmap(back,null,backgroundrepeatleft, null);


            //Drawing Mario
            manageCurrentFrame(); //Managing which frame of Mario will be displayed
            bitmap = player.getBitmap(); //get Mario Bitmap from Player class
            bitmap = Bitmap.createScaledBitmap(bitmap, player.getFrameWidth() * frameCount, player.getFrameHeight(), false);
            whereToDraw.set((int) player.getmanXPos(), (int) player.getmanYPos(),
                    (int) player.getmanXPos() + player.getFrameWidth(), player.getmanYPos() + player.getFrameHeight());
            canvas.drawBitmap(player.getBitmap(),frameToDraw,whereToDraw,null);


            //Drawing Score
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Score: " + levelOne.getScore(), getWidth()/2, 60, paint);

            //Drawing Coins
            levelCoins = levelOne.getCoinLoc();
            if(levelOne.getCoinLoc() != null) {
                for (int i = 0; i < levelOne.getCoinLoc().size(); i++) {
                    Rect c = new Rect(levelCoins.get(i).left, levelCoins.get(i).top, levelCoins.get(i).right, levelCoins.get(i).bottom);
                    canvas.drawBitmap(levelOne.getCoinBitmap(), null, c, null);
                }
            }

            //Drawing Blocks
            levelBlocks = levelOne.getBlockLoc();
            for(int i = 0; i < levelOne.getBlockLoc().size(); i++){
                Rect b = new Rect(levelBlocks.get(i).left ,levelBlocks.get(i).top,levelBlocks.get(i).right ,levelBlocks.get(i).bottom);
                canvas.drawBitmap(levelOne.getBlockBitmap().get(i), null, b, null);
            }

            //Drawing Flowers
            levelFlowers = levelOne.getFlowerLoc();
            if(levelOne.getFlowerLoc() != null) {
                for (int i = 0; i < levelOne.getFlowerLoc().size(); i++) {
                    Rect f = new Rect(levelFlowers.get(i).left, levelFlowers.get(i).top, levelFlowers.get(i).right, levelFlowers.get(i).bottom);

                    if(levelOne.marioHitItemBlock() && (levelFlowers.get(i).top <= levelOne.getHitBlockLoc())){
                        canvas.drawBitmap(levelOne.getFlowerBitmap(), null, f, null);
                    }

                    //canvas.drawBitmap(levelOne.getFlowerBitmap(), null, f, null);
                }
            }

            //Drawing Mushrooms
            levelMushrooms = levelOne.getMushroomLoc();
            if(levelOne.getMushroomLoc() != null) {
                for (int i = 0; i < levelOne.getMushroomLoc().size(); i++) {
                    Rect f = new Rect(levelMushrooms.get(i).left, levelMushrooms.get(i).top, levelMushrooms.get(i).right, levelMushrooms.get(i).bottom);

                    /*
                    if(levelOne.marioHitItemBlock() && (levelMushrooms.get(i).top <= levelOne.getHitBlockLoc())){
                        canvas.drawBitmap(levelOne.getMushroomBitmap(), null, f, null);
                    }
                    */
                    canvas.drawBitmap(levelOne.getMushroomBitmap(), null, f, null);
                }
            }

            //Drawing Enemies
            levelEnemies = levelOne.getEnemyLoc();
            if(levelOne.getEnemyLoc() != null) {
                for (int i = 0; i < levelOne.getEnemyLoc().size(); i++) {
                    Rect f = new Rect(levelEnemies.get(i).left, levelEnemies.get(i).top, levelEnemies.get(i).right, levelEnemies.get(i).bottom);
                    canvas.drawBitmap(levelOne.getEnemyBitmap().get(i), null, f, null);
                }
            }

            //Drawing Lives
            lives = levelOne.getLifeArray();
            if(levelOne.getLifeArray() != null) {
                for (int i = 0; i < levelOne.getLifeArray().size(); i++) {
                    heart = lives.get(i);
                    Rect life = new Rect(10 + (heart.getWidth() * i), 10, heart.getWidth() + (heart.getWidth() * i), heart.getHeight());
                    canvas.drawBitmap(heart, null, life, null);

                }
            }
            else
            {
                Paint paint1 = new Paint();
                paint1.setColor(Color.BLACK);
                canvas.drawRect(0,0,screenWidth,screenHeight, paint1);


                paint.setColor(Color.WHITE);
                paint.setTextSize(200);
                canvas.drawText("GAME OVER", 350, screenHeight/2 + 100, paint);

            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        //control the frames per seconds drawn
        try {
            gameThread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);

        int move = 0; //L to R = 1, R to L = -1, U to D = 2

        switch(action/*event.getAction() & MotionEvent.ACTION_MASK*/){
            case MotionEvent.ACTION_MOVE: {


                final int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                final float x = MotionEventCompat.getX(event, pointerIndex);
                final float y = MotionEventCompat.getY(event, pointerIndex);
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                touchPosX += dx;
                touchPosY += dy;

                invalidate();

                //Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;





                if (dx > 0) {
                    move = 1;
                } else if (dx < 0) {
                    move = -1;
                } else if (dy > 0) {
                    move = 2;
                } else if(dy < 0){
                    move = -2;
                }
                else {}


                if (move == 1) {
                    System.out.println("L to R");
                    player.setRun(1);
                    motion = 1;
                } else if (move == -1) {
                    System.out.println("R to L");
                    player.setRun(2);
                } else if (move == 2) {
                    System.out.println("U to D");
                } else if(move == -2){
                    System.out.println("D to U");
                    control();
                    control();
                    player.jump();
                    jumpTimeStart = System.currentTimeMillis();
                    control();
                    break;
                }
                else {
                    System.out.println("Error occurred");
                }
                break;
            }

            case MotionEvent.ACTION_UP: {

                player.stopRun();
                //player.canceljump();
                motion = 0;
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                final int pointIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointIndex);
                final float y = MotionEventCompat.getY(event, pointIndex);

                System.out.println("ACTION_DOWN");

                //Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                //Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            }
        }
        return true;
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        //here we are stopping the gameThread
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
