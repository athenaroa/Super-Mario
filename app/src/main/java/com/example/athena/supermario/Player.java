package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    //Bitmap to get Character from image
    private Bitmap runningMario;
    private Bitmap runningMarioLEFT;
    private Bitmap jumpingMario;
    private Bitmap jumpingMarioLEFT;
    private Bitmap superjumpingMario;
    private int marioType = 1;
    private int direction;
    private boolean jump;
    private boolean run;
    private boolean marioHitTop;
    private int marioSpeed;

    //Max X coordinate so mario does not go out of screen
    private int maxX;
    private int minX;


    private int frameWidth = 190, frameHeight = 240;

    private float manXPos, manYPos;
    private float prevmanXPos, prevmanYPos;





    //constructor
    public Player(Context context, int screenX, int screenY) {

        marioSpeed = 10;
        manXPos = 10;
        manYPos = (frameHeight * 5) + 200;


        //Getting bitmap from drawable resource
        runningMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmario);
        jumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.normaljumpmario);
        runningMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.normalrunmmarioleft);
        jumpingMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.normaljumpmarioleft);
        superjumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmario);


        maxX = screenX - runningMario.getWidth();
        minX = 0;

        jump = false;
        run = false;
        marioHitTop = false;

    }

    public int getMarioSpeed() {
        return marioSpeed;
    }

    public int getDirection(){
        return direction;
    }


    public void setRun(int direc){
        //direction = 1: right
        //direction = 2: left
        direction = direc;
        System.out.print("Direction = " + direction);
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

    public void setmanXPos(int hit, int width){
        prevmanXPos = getmanXPos();

        if(run && !jump && (hit == 0))
        {
            System.out.println("Run and NOT jump and NOT hit");
            if(direction == 2) {
                this.manXPos -= marioSpeed;
                //System.out.println("manXPos = " + manXPos);
                if (prevmanXPos < (frameWidth * 2)) {
                    this.manXPos = frameWidth * 2;
                }
            }
            else //Direction is to the right
            {
                this.manXPos += marioSpeed;
                if (prevmanXPos > (width - (frameWidth * 2))) {
                    this.manXPos = width - (frameWidth * 2);
                }
            }
        }
        else if(run && !jump && (hit == 1)){
            //System.out.println("");
            if(direction == 2){
                this.manXPos = prevmanXPos + 10;
            }
            else {
                this.manXPos = prevmanXPos - 10;
            }
        }

        else if(!run && jump && (hit == 0 )){
            System.out.println("NOT run and jump and NOT hit");
            this.manXPos = prevmanXPos + (marioSpeed/4);
            if(manXPos > (width - (frameWidth * 2) )){
                this.manXPos = width - (frameWidth * 2);
                //canceljump();
            }
        }
        else if(!run && !jump)
        {
            //this.manXPos = prevmanXPos;
            //No move should occur
        }
        else if(run && jump){
            System.out.println("Run and Jump");
            manXPos += 20;
            canceljump();
        }
        else {
            System.out.println("Else");
            System.out.println("Run = " + run);
            System.out.println("Jump = " + jump);
            System.out.println("Hit = " + hit);

            /*
            this.manXPos = manXPos + (increment);
            if(manXPos > (width - (frameWidth * 2) )){
                this.manXPos = width - (frameWidth * 2);
            }
            */
        }
    }

    public void setmanYPos(int hit, float newYPos){

        //System.out.println("Increment = " + increment);
        prevmanYPos = getmanYPos();

        if((run && !jump && (hit == 0)) ||(run && !jump && (hit == 1)) )
        {
            //this.manYPos = prevmanYPos;
        }
        else if (!run && jump && (hit == 1))
        {
            //this.manYPos = newYPos;
            //No movement should occur
        }
        else if(!run && jump && (hit == 0)){
            if( (manYPos <= frameHeight))
            {
                marioHitTop = true;
                //this.manYPos = frameHeight;
                this.manYPos += marioSpeed/2;
                jump = false;

                System.out.println("Mario hit the top of the frame : " + frameHeight);
                System.out.println("Jump = false");
            }
            else {
                this.manYPos -=  marioSpeed/2;
            }
        }
        else if ((!run && !jump && (hit == 0))){
            if(marioHitTop)
            {
                this.manYPos += marioSpeed/10;
            }
            if(manYPos > ((frameHeight * 5) + 200))
            {
                marioHitTop = false;
                this.manYPos = (frameHeight * 5) + 200;
            }
        }
        else if (!run && !jump && (hit == 1))
        {
            this.manXPos = newYPos;
        }
        else
        {

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
        else if (!jump && (direction == 1))
        {
            marioType = 1;
            return runningMario;
        }
        else if (!jump && (direction == 2)) //same mario frame but mario going left
        {
            marioType = 1;
            return runningMarioLEFT;
        }
        else if (!jump && (direction == 2))
        {
            marioType = 2;
            return jumpingMarioLEFT;
        }
        else
        {
            return runningMario;
        }

    }
}
