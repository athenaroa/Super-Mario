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
    private boolean run;

    //Max X coordinate so mario does not go out of screen
    private int maxX;
    private int minX;

    //Limit the speed of mario
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;


    private int frameWidth = 190, frameHeight = 240;

    private float manXPos, manYPos;
    private int speed = 0;


    //constructor
    public Player(Context context, int screenX, int screenY) {

        manXPos = 10;
        manYPos = (frameHeight * 5) + 200;
        speed = 1;

        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
        jumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normaljumpmario);
        superjumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmario);


        maxX = screenX - runningMario.getWidth();
        minX = 0;

        jump = false;
        run = false;
    }
    public void setRun(){
        run = true;
    }

    public void stopRun(){
        run = false;
    }

    public int getFrameWidth(){


        switch(marioType){
            case 1: //normal running mario
                frameWidth = 150;
                break;
            case 2: //normal jumping mario
                frameWidth = 150; //super jumping mario 165
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
                frameHeight = 150;
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
                manYPos = (frameHeight * 5) + 200;
                break;
            case 2: //normal jumping mario
                //manYPos = (frameHeight * 5) - 200;
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
        if(run)
        {
            this.manXPos = manXPos + increment;
            if(manXPos > (width - (frameWidth * 2) )){
                this.manXPos = width - (frameWidth * 2);
            }
        }
        if(!jump){
            this.manXPos = manXPos + increment;
            if(manXPos > (width - (frameWidth * 2) )){
                this.manXPos = width - (frameWidth * 2);
            }
        }

    }

    public void setmanYPos(float increment, int height){
        if(jump) {
            this.manYPos = manYPos - increment;
        }
        if(!jump){
            this.manYPos = manYPos + increment;
            if(manYPos > ((frameHeight * 5) + 200))
            {
                this.manYPos = (frameHeight * 5) + 200;
            }
        }
    }

    public void jump(){
        this.jump = true;
    }

    public void canceljump(){
        this.jump = false;
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
