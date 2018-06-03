package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Mario extends AppCompatActivity {

    private GameView gameView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    class GameView extends SurfaceView implements Runnable {
        private Thread gameThread;
        private SurfaceHolder ourHolder;
        private volatile boolean playing;
        private Canvas canvas;
        private Bitmap bitmapRunningMario;
        private boolean isMoving;
        private float runSpeedPerSecond = 250;
        private float manXPos = 10, manYPos = 10;
        private int frameWidth = 115, frameheight = 137;
        private int frameCount = 8;
        private int currentFrame = 0;
        private long fps;
        private long timeThisFrame;
        private long lastFrameChange = 0;
        private int frameLengthInMillisecond = 100;

        private Rect frameToDraw = new Rect(0, 0, frameWidth, frameheight);

        private RectF whereToDraw = new RectF((int)manXPos,(int)manYPos,(int) manXPos + frameWidth, frameheight);


        private GameView(Context context) {
            super(context);
            ourHolder = getHolder();
            bitmapRunningMario = BitmapFactory.decodeResource(getResources(), R.drawable.normalrunmmario);
            bitmapRunningMario = Bitmap.createScaledBitmap(bitmapRunningMario, frameWidth * frameCount, frameheight, false);
        }

        @Override
        public void run() {
            while (playing) {
                long startFrameTime = System.currentTimeMillis();
                update();
                draw();

                timeThisFrame = System.currentTimeMillis() - startFrameTime;

                if (timeThisFrame >= 1) {
                    fps = 100 / timeThisFrame;
                }
            }
        }

        public void update() {
            if (isMoving) {
                manXPos = manXPos + runSpeedPerSecond / fps;
                if (manXPos > getWidth()) {
                    manXPos += (int) frameheight;
                    manXPos = 10;
                }

                if (manYPos + frameheight > getHeight()) {
                    manYPos = 10;
                }
            }

        }

        public void manageCurrentFrame() {
            long time = System.currentTimeMillis();

            if (isMoving) {
                if (time > lastFrameChange + frameLengthInMillisecond) {
                    lastFrameChange = time;
                    currentFrame++;

                    if(currentFrame >= frameCount){
                        currentFrame = 0;
                    }

                }
            }

            frameToDraw.left = currentFrame + frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;

        }

        public void draw(){
            if(ourHolder.getSurface().isValid()){
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                whereToDraw.set((int) manYPos, (int) manYPos, (int) manXPos + frameWidth, (int) manYPos + frameheight);
                manageCurrentFrame();
                canvas.drawBitmap(bitmapRunningMario,frameToDraw,whereToDraw,null);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void pause(){
            playing = false;

            try{
                gameThread.join();
            } catch(InterruptedException e){
                Log.e("Err", "Joining Thread");
            }
        }

        public void resume(){
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    isMoving = isMoving;
                    break;

            }



            return true;
        }
    }
}

















