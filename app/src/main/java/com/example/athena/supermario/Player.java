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



    //constructor
    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 5000;
        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
    }

    public void update(){
        x = 10;
    }

    public Bitmap getBitmap() {
        return runningMario;
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
