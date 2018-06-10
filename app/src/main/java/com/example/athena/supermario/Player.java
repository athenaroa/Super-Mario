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

    private Bitmap superRunMario;
    private Bitmap superjumpingMario;
    private Bitmap superRunMarioLEFT;
    private Bitmap superJumpMarioLEFT;


    private Bitmap fireRunMario;
    private Bitmap fireJumpMario;
    private Bitmap fireRunMarioLEFT;
    private Bitmap fireJumpMarioLEFT;

    private int marioType; //reg running, reg jump...
    private int marioForm; //reg, super, fire



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
    private boolean marioHitABlock;
    private boolean marioHitGround;







    //constructor
    public Player(Context context, int screenX, int screenY) {


        marioType = 1;
        marioForm = 1;

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

        superRunMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superrunmario);
        superjumpingMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmario);
        superRunMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.superrunmarioleft);
        superJumpMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.superjumpmarioleft);


        fireRunMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.firemario);
        fireJumpMario = BitmapFactory.decodeResource(context.getResources(),R.drawable.firemariojump);
        fireRunMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.firemarioleft);
        fireJumpMarioLEFT = BitmapFactory.decodeResource(context.getResources(),R.drawable.firemariojumpleft);



        jump = false;
        run = false;
        marioHitTop = false;
        marioOnBlock = false;
        marioHitABlock = false;
        marioHitGround = true;

        screenHeight = screenY;
        screenWidth = screenX;

        maxX = screenX - frameWidth;
        minX = frameWidth;


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
                frameWidth = 165; //super jumping mario 165
                break;
            case 4: //SUPER jumping mario
                frameWidth = 165;
                break;
            case 5: //fire running mario
                frameWidth = 180;
                break;
            case 6: //fire jumping mario
                frameWidth = 180;
                break;
        }
        return frameWidth;
    }


    public int getFrameHeight(){

        switch(marioType){
            case 1: //normal running mario
                frameHeight = 150;
                break;
            case 2: //normal jumping mario
                frameHeight = 200;
                break;

            case 3: //SUPER running mario
                frameHeight = 220; //super jumping mario 220
                break;
            case 4: //SUPER jumping mario
                frameHeight = 220;
                break;


            case 5: //fire running mario
                frameHeight = 220;
                break;
            case 6:  //fire jumping mario
                frameHeight = 220;
                break;
        }
        return frameHeight;
    }

    public float getmanXPos(){
        return manXPos;
    }

    public float getmanYPos(){
        if(marioType == 1){
            this.manYPos = (frameHeight * 5) + 200;
        }
        else if (marioType == 3){
            this.manYPos = (frameHeight * 3) + 160;
        }
        else if (marioType == 5){
            this.manYPos = (frameHeight * 3) + 160;
        }
        return manYPos;
    }

    public void updateMarioPos(){
       updateX();
       updateY();
    }

    public void runRight(){
        this.manXPos += marioSpeed;
        if (prevmanXPos > maxX) { //Mario is all the way to the right of the screen
            this.manXPos = maxX;
        }
    }

    public void runLeft(){
        this.manXPos -= marioSpeed;
        if (prevmanXPos < minX) { //Mario is all the way to the left of the screen
            this.manXPos = minX;
        }
    }


    public void updateX(/*int hit, int width*/){
        prevmanXPos = getmanXPos();

        if(run && !jump) //Mario is running
        {
            //System.out.println("Run and NOT jump and NOT hit");
            if(marioHitABlock)
            {
                if(marioOnBlock){
                    if(direction == 2) {
                        runLeft();
                    }
                    else //Direction is to the right
                    {
                        runRight();
                    }
                }
                else {
                    if (direction == 2) { //Direction is to the left
                        this.manXPos = prevmanXPos + 100; //Pushing mario to the right away from block
                        marioHitABlock = false; //Set mario hit a block now to false
                    } else {
                        this.manXPos = prevmanXPos - 100; //Pushing mario to the left away from block
                        marioHitABlock = false; //Set mario hit a block now to false
                    }
                }
            }

            else {
                if (direction == 2) {
                    runLeft();
                } else //Direction is to the right
                {
                    runRight();
                }
            }
        }

        else if(!run && !jump) //Mario is not running or jumping
        {
            //No move should occur
        }
        else if (!run && jump){ //Mario is jumping
            if(direction == 2){
                this.manXPos -= marioSpeed ;
                if (prevmanXPos < minX) {
                    this.manXPos = minX;
                }
            }
            else{
                this.manXPos += marioSpeed ;
                if (prevmanXPos > maxX) {
                    this.manXPos = maxX;
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
            //System.out.println("Hit = " + hit);
        }
    }

    public void setMarioOnBlock(boolean result){
        this.marioOnBlock = result;
    }

    public void setMarioHitABlock(boolean result) {
        this.marioHitABlock = result;
    }

    public void checkMarioHitTop(){
        if( (manYPos <= ( screenHeight/2 - frameHeight) )) //
        {
            marioHitTop = true;
            System.out.println("Mario hit the top of the frame");
        }
    }

    public void checkMarioHitGround(){


        if(marioType == 1 || marioType == 2){
            if((manYPos + frameHeight >= ((frameHeight * 5) + 200))){
                System.out.println("Regular Mario hit the ground");
                marioType = 1;
                marioHitGround = true;
                this.manYPos = (frameHeight * 5) + 200;
                marioHitTop = false;
                canceljump(); //Cancel jump because mario should move down anymore
            }
        }
        else if (marioType == 3 || marioType == 4){
            if((manYPos + frameHeight >= (frameHeight * 3) + 160)){
                System.out.println(" Super Mario hit the ground");
                marioType = 3;
                marioHitGround = true;
                this.manYPos = (frameHeight * 3) + 160;
                marioHitTop = false;
                canceljump(); //Cancel jump because mario should move down anymore
            }

        }
        else if (marioType == 5 || marioType == 6){
            if((manYPos + frameHeight >= (frameHeight * 3) + 160)){
                System.out.println(" Fire Mario hit the ground");
                marioType = 5;
                marioHitGround = true;
                this.manYPos = (frameHeight * 3) + 160;
                marioHitTop = false;
                canceljump(); //Cancel jump because mario should move down anymore
            }
        }


    }

    public void updateY( ){

        prevmanYPos = getmanYPos();

        if((run && !jump && !marioHitABlock ) ||(run && !jump && marioHitABlock) )
        {
            //No movement in y-direction should occur
            //mario is running and doesnt hit anything
            //or Mario is running and hits an item which means no change in Y should happen
        }
        else if(!run && jump){ //Mario jumping only

             checkMarioHitTop();
             if (marioHitTop || (marioHitABlock)){ //if Mario hit the top or hit A BLOCK

                 if (marioHitABlock){
                    System.out.println("Mario hits a block");
                    if(marioOnBlock){
                        System.out.printf("Mario on top of a block");
                        manYPos = prevmanYPos;
                        canceljump();
                        marioHitTop = false;
                    }
                    else
                    {
                        System.out.println("Moving down from block");
                        this.manYPos +=  marioSpeed*2; //Mario moving down
                        marioHitTop = false; //can no longer hit the top if it hit the block
                        canceljump();
                    }
                 }
                 else{
                     System.out.println("Moving down at this point");
                     this.manYPos +=  marioSpeed*2; //Mario moving down

                 }
                 checkMarioHitGround();
            }
            else {
                //System.out.println("Mario moving up");
                //System.out.println("manYPos before:" + manYPos);
                this.manYPos -=  marioSpeed*2;
                //System.out.println("manYPos after:" + manYPos);
            }
        }
        else if (!run && !jump ){ //Mario is not running or jumping; potentially falling or standing still
            //System.out.println("SetmanYPos: Conditional 4");
            if(marioOnBlock){
                System.out.println("Mario is on top of block");
                this.manYPos = prevmanYPos;
                //Mario is on the block do not change Y

                if(marioHitABlock){
                    System.out.printf("Mario is still hitting the block");
                    //this.manYPos = newYPos;
                }
                else
                {
                    System.out.println("marioOnBlock is now false");
                    marioOnBlock = false;
                }
            }
            else
            {
                //System.out.println("Mario is not on top of block: !run and !jump");
                this.manYPos +=  marioSpeed; //Mario moving down
                checkMarioHitGround();
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

    public void updateMarioForm(int newForm){
        //1 : reg mario
        //2 : super mario
        //3 : fire mario
        this.marioForm = newForm;
    }

    public Bitmap getBitmap() {
        Bitmap m = runningMario;

        if(marioForm == 1) {
            if(run)
            {
                marioType = 1;
                if (direction == 2)
                {
                  m = runningMarioLEFT;
                }
                else {
                    m = runningMario;
                }
            }
            else if (jump){
                marioType = 2;
                if(direction == 2){
                    m = jumpingMarioLEFT;
                }
                else
                {
                    m = jumpingMario;
                }
            }
            else {
                m = runningMario;
            }
        }
        else if (marioForm == 2)
        {
            if(run)
            {
                marioType = 3;
                if (direction == 2)
                {
                    m = superRunMarioLEFT;
                }
                else {
                    m = superRunMario;
                }
            }
            else if (jump){
                marioType = 4;
                if(direction == 2){
                    m = superJumpMarioLEFT;
                }
                else
                {
                    m = superjumpingMario;
                }
            }
            else {
                m = superRunMario;
            }
        }
        else if (marioForm == 3)
        {
            if(run)
            {
                marioType = 5;
                if (direction == 2)
                {
                    m = fireRunMarioLEFT;
                }
                else {
                    m = fireRunMario;
                }
            }
            else if (jump){
                marioType = 6;
                if(direction == 2){
                    m = fireJumpMarioLEFT;
                }
                else
                {
                    m = fireJumpMario;
                }
            }
            else {
                m = fireRunMario;
            }
        }
        else
        {
            m = runningMario;
        }
        return m;

    }
}
