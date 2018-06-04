package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    //Bitmap to get Character from image
    private Bitmap runningMario;

    //coordinates
    private int x;
    private int y;

    //motion speed of character
    private int speed = 0;

    private float runSpeedPerSecond = 200;
    private int frameWidth = 120, frameHeight = 120;
    private float manXPos = 10, manYPos = frameHeight * 5;
    private int frameCount = 6;
    private int currentFrame = 0;
    private long fps;
    private long timeThisFrame;
    private long lastFrameChange = 0;
    private int frameLengthInMillisecond = 20;

    //constructor
    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 5000;
        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
    }

    public void update(){
        //updating x coordinate
        x++;
    }

    public Bitmap getBitmap() {
        return runningMario;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
