package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    //Bitmap to get Character from image
    private Bitmap runningMario;
    private Bitmap jumpingMario;
    private Bitmap superjumpingMario;
    private int marioType = 1;
    private boolean jump;

    //private float runSpeedPerSecond = 200;
    private int frameWidth = 190, frameHeight = 240;
    private float manXPos = 10, manYPos = (frameHeight * 2) + 100 ;
   // private int frameCount = 6;
    //private int currentFrame = 0;
    //private long fps;
    //private long timeThisFrame;
    //private long lastFrameChange = 0;
    //private int frameLengthInMillisecond = 20;

    //constructor
    public Player(Context context) {
        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
        jumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normaljumpmario);
        superjumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmario);

        jump = false;
    }

    public void setMarioType(int changeMT){
        this.marioType = changeMT;
    }

    public int getFrameWidth(){


        switch(marioType){
            case 1: //normal running mario
                frameWidth = 260;
                break;
            case 2: //normal jumping mario
                frameWidth = 260; //super jumping mario 165
                break;
            case 3: //SUPER running mario
                frameWidth = 0; //super jumping mario 165
                break;
            case 4: //SUPER jumping mario
                frameWidth = 165;
                break;
        }
        return frameWidth;
    }


    public int getFrameHeight(){

        switch(whichMario()){
            case 1: //normal running mario
                frameHeight = 200;
                break;
            case 2: //normal jumping mario
                frameHeight = 200; //super jumping mario 220
                break;
            case 3: //SUPER running mario
                frameHeight = 0; //super jumping mario 220
                break;
            case 4: //SUPER jumping mario
                frameHeight = 220;
                break;
        }
        return frameHeight;
    }

    public float getmanXPos(){
        return manXPos;
    }

    public float getmanYPos(){
        switch(whichMario()){
            case 1: //normal running mario
                manYPos = (frameHeight * 2) + 100;
                break;
            case 2: //normal jumping mario
                manYPos = (frameHeight * 2) - 100;
                break;
            case 3: //SUPER running mario
                manYPos = 0;
                break;
            case 4: //SUPER jumping mario
                manYPos = 0;
                break;
        }
        return manYPos;
    }


    public void setmanXPos(float increment, int width){
         this.manXPos = manXPos + increment;
        if(manXPos > width ){
            this.manXPos = 10;
        }
    }

    public void setmanYPos(float increment){
        this.manXPos = manXPos + increment;
    }

    public void jump(){
        jump = !jump;
    }

    public int whichMario(){
        return marioType;
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
}
