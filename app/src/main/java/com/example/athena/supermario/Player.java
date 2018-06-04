package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    //Bitmap to get Character from image
    private Bitmap runningMario;
    private Bitmap jumpingMario;
    private int marioType = 0;

    //coordinates
    private int x;
    private int y;

    //motion speed of character
    private int speed = 0;

    private boolean jump;




    //constructor
    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 1;
        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
        jumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmario);
        jump = false;
    }

    public void jump(){
        jump = !jump;
    }

    public int whichMario(){
        return marioType;
    }

    public void update(){
        x = 10;
    }

    public Bitmap getBitmap() {
        if(jump == true)
        {
            marioType = 2;
            return jumpingMario;
        }
        else
        {
            marioType = 1;
            return runningMario;
        }

    }

    public int getX() {
        System.out.println("x = " + x);
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
