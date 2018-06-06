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

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class GameView extends SurfaceView implements Runnable {
    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;
    //adding the player to this class
    private Player player;



    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    private int motion;
    private float runSpeedPerSecond = 100; //200
    private int frameCount = 6;
    private int currentFrame = 0;



    private long fps;
    private long timeThisFrame;
    private long lastFrameChange = 0;
    private int frameLengthInMillisecond = 60;

    private long jumpTimeStart;
    private long jumpTimeMax;


    private int backPosX;


    private Rect frameToDraw = new Rect(0, 0, 190, 240);
    private RectF whereToDraw = new RectF(10, 580,
            10 + 190, 240);


    private int mActivePointerId = INVALID_POINTER_ID;
    private float mLastTouchX;
    private float mLastTouchY;
    public float touchPosX;
    public float touchPosY;



    Bitmap bitmap;
    Bitmap background1;
    Bitmap background1repeatright;
    Bitmap background1repeatleft;



    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //initializing player object
        player = new Player(context, screenX,screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        background1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        background1repeatright = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        background1repeatleft = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);

        motion = 0;
        backPosX = 0;

        jumpTimeStart = 0;
        jumpTimeMax = 200;

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
        //update the coordinates of our character
        player.setmanXPos(runSpeedPerSecond/fps, getWidth());
        player.setmanYPos((runSpeedPerSecond/fps) * 10, getHeight());

        //update coordinates of the background
        if(motion == 1 && (player.getmanXPos() > (getWidth()* 3)/4) && (player.getDirection() == 1)) {
            backPosX += 5;
            if(backPosX > getWidth())
            {
                backPosX = 0;
            }
        }
        else if(motion == 1 && (player.getmanXPos() < (getWidth()/4)) && (player.getDirection() == 2)) {
            backPosX -= 5;
            if(backPosX < 0- getWidth())
            {
                backPosX = 0;
            }
        }
        else{}


        //Update coordinates of jump
        if((System.currentTimeMillis() - jumpTimeStart) >= jumpTimeMax)
        {
            player.canceljump();
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
    private void draw() {
        //draw the character to the canvas
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            //canvas.drawColor(Color.WHITE);

            Rect background = new Rect(0 - backPosX,0,getWidth() - backPosX,getHeight());
            Rect backgroundrepeatright = new Rect((getWidth() - backPosX),0,(getWidth() * 2) - backPosX,getHeight());
            Rect backgroundrepeatleft = new Rect((0 - getWidth() - backPosX),0, 0 - backPosX,getHeight());


            canvas.drawBitmap(background1,null,background,null);
            canvas.drawBitmap(background1repeatright,null,backgroundrepeatright, null);
            canvas.drawBitmap(background1repeatleft,null,backgroundrepeatleft, null);

            bitmap = player.getBitmap();
            manageCurrentFrame();

            bitmap = Bitmap.createScaledBitmap(bitmap, player.getFrameWidth() * frameCount, player.getFrameHeight(), false);

            whereToDraw.set((int) player.getmanXPos(), (int) player.getmanYPos(),
                    (int) player.getmanXPos() + player.getFrameWidth(), player.getmanYPos() + player.getFrameHeight());
            canvas.drawBitmap(player.getBitmap(),frameToDraw,whereToDraw,null);
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
                    player.jump();
                    jumpTimeStart = System.currentTimeMillis();
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

                /*
                final int pointIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointIndex);
                final float y = MotionEventCompat.getY(event, pointIndex);

                System.out.println("ACTION_UP");
                //Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                //Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                */
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                //player.jump();
                //resume(); //resume forward
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
