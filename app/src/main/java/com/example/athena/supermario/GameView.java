package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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


    private float runSpeedPerSecond = 200;
    //private int frameWidth = 190, frameHeight = 240;
   // private float manXPos = 10, manYPos = (frameHeight * 2) + 100 ;
    private int frameCount = 6;
    private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;
    private long lastFrameChange = 0;
    private int frameLengthInMillisecond = 20;

    private Rect frameToDraw = new Rect(0, 0, 190, 240);
    private RectF whereToDraw = new RectF(10, 580,
            10 + 190, 240);


    Bitmap bitmap;

    //Class constructor
    public GameView(Context context) {
        super(context);

        //initializing player object
        player = new Player(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

    }

    @Override
    public void run() {
        while (playing) {

            long startFrameTime = System.currentTimeMillis();
            //to update the frame
            update();
            //to draw the frame
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1){
                fps = 1000 / timeThisFrame;
            }
            //to control
            control();
        }
    }


    private void update() {
        //update the coordinates of our character
        player.setmanXPos(runSpeedPerSecond/fps, getWidth());

        /*
        manXPos = manXPos + runSpeedPerSecond / fps;
        if(manXPos > getWidth()){
            manXPos = 10;
        }
        */
        //update the coordinates of Items
    }

    public void manageCurrentFrame() {
        long time = System.currentTimeMillis();

        if (time > lastFrameChange + frameLengthInMillisecond)
        {
            lastFrameChange = time;
            currentFrame++;

            if (currentFrame >= frameCount)
            {
                    currentFrame = 0;
            }
        }
        frameToDraw.left = currentFrame * player.getFrameWidth();
        frameToDraw.right = frameToDraw.left + player.getFrameWidth();
    }
    private void draw() {
        //draw the character to the canvas
        //Checking if surface is valid
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            //paint.setColor(Color.GREEN);
            //canvas.drawRect(0,0,getWidth()/4,getHeight()/4,paint);

            bitmap = player.getBitmap();

            /*
            switch(player.whichMario()){
                case 1: //normal running mario
                    frameWidth = 260;
                    frameHeight = 200;
                    manYPos = (frameHeight * 2) + 100;
                    break;
                case 2: //normal jumping mario
                    frameWidth = 260; //super jumping mario 165
                    frameHeight = 200; //super jumping mario 220
                    manYPos = (frameHeight * 2) - 100;
                    break;
                case 3: //SUPER running mario
                    frameWidth = 0; //super jumping mario 165
                    frameHeight = 0; //super jumping mario 220
                    manYPos = 0;
                    break;
                case 4: //SUPER jumping mario
                    frameWidth = 165;
                    frameHeight = 220;
                    manYPos = 0;
                    break;
            }
            */

            //frameToDraw= new Rect(0, 0, player.getFrameWidth(), player.getFrameHeight());

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
            gameThread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                player.jump();
                break;
            case MotionEvent.ACTION_DOWN:
                player.jump();
                break;
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
