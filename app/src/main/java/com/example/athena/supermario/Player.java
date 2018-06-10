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
    private int screenHeight, screenWidth;

    private float manXPos, manYPos;
    private float prevmanXPos, prevmanYPos;


    private float manXPosRight, manYPosRight;


    private boolean marioOnBlock;







    //constructor
    public Player(Context context, int screenX, int screenY) {

        marioSpeed = 10;
        manXPos = 10;
        manYPos = (frameHeight * 5) + 200;
        manXPosRight = manXPos + frameWidth;
        manYPosRight = manYPos + frameHeight;


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
        marioOnBlock = false;

        screenHeight = screenY;
        screenWidth = screenX;

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
        //System.out.println("Direction = " + direction);
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
            //System.out.println("Run and NOT jump and NOT hit");
            if(direction == 2) {
                this.manXPos -= marioSpeed;
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
                this.manXPos = prevmanXPos + 30;
            }
            else {
                this.manXPos = prevmanXPos - 30;
            }
        }
        else if(!run && !jump)
        {
            //No move should occur
        }
        else if (!run && jump){
            if(direction == 2){
                this.manXPos -= marioSpeed/2 ;
                if (prevmanXPos < (frameWidth * 2)) {
                    this.manXPos = frameWidth * 2;
                }
            }
            else{
                this.manXPos += marioSpeed/2 ;
                if (prevmanXPos > (width - (frameWidth * 2))) {
                    this.manXPos = width - (frameWidth * 2);
                }
            }
        }
        else if(run && jump){
            //stopRun();
            canceljump();
            System.out.println("Run and Jump , no more should occur");
        }
        else {
            System.out.println("Else");
            System.out.println("Run = " + run);
            System.out.println("Jump = " + jump);
            System.out.println("Hit = " + hit);
        }
    }



    public void updateMarioPos(){
    }


    public void checkMarioHitTop(){
        if( (manYPos <= ( screenHeight/2 - frameHeight) )) //
        {
            marioHitTop = true;
            System.out.println("Mario hit the top of the frame");
        }
        else
        {
            marioHitTop = false;
        }

    }

    public void setmanYPos(int hit, float newYPos, int hitType, float blockXPos, float blockWidth){

        prevmanYPos = getmanYPos();

        if((run && !jump && (hit == 0)) ||(run && !jump && (hit == 1)) )
        {
            //No movement in y-direction should occur
        }
        else if(!run && jump){ //Mario jumping only

             if (marioHitTop || (hit == 1)){ //if Mario hit the top or hit A BLOCK

                 this.manYPos +=  marioSpeed*2; //Mario moving down
                 /*
                 if(marioHitTop){
                     this.manYPos +=  marioSpeed*2;
                 }
                 */
                /*else*/ if (hit == 1){
                    System.out.println("Mario hits a block");
                    System.out.println("Hit type: " + hitType);
                    //marioHitTop = false;
                    //this.manYPos +=  marioSpeed*2;
                 }
                if((manYPos + frameHeight >= ((frameHeight * 5) + 200))){
                    System.out.println("Mario hit the ground");
                     this.manYPos = (frameHeight * 5) + 200;
                     marioHitTop = false;
                     canceljump(); //Cancel jump because mario should move down anymore
                }

            }
            else if( (manYPos <= ( screenHeight/2 - frameHeight) )) //
            {
                marioHitTop = true;
                System.out.println("Mario hit the top of the frame");

            }
            else {
                //System.out.println("Mario moving up");
                //System.out.println("manYPos before:" + manYPos);
                this.manYPos -=  marioSpeed*2;
                //System.out.println("manYPos after:" + manYPos);
            }
        }
        else if (!run && !jump ){
            //System.out.println("SetmanYPos: Conditional 4");
            if(marioOnBlock){
                System.out.println("Mario is on top of block");
                if(hit == 1){
                    System.out.printf("Mario is still hitting the block");
                    this.manYPos = newYPos;
                }
                else
                {
                    System.out.println("marioOnBlock is now false");
                    marioOnBlock = false;
                }
            }
            else {
                this.manYPos = (frameHeight * 5) + 200;
            }
        }
        else if (run && jump){
            System.out.println("Run and Jump setY");
        }
        else
        {
            System.out.println("SetmanYPos: Else");
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
