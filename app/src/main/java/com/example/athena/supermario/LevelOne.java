package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class LevelOne {

    Bitmap background;
    private int backFrame;

    public int lives;
    Bitmap heart;
    private float marioLeftX, marioLeftY, marioRightX, marioRightY;

    private int screenWidth, screenHeight;

    private ArrayList<Bitmap> lifeArray;
    private ArrayList<Rect> coinLoc;
    private ArrayList<Rect> blockLoc;
    private int score;

    private Coins coin;
    private Blocks block;
    private float marioNewYPos;


    public LevelOne(Context context, int screenX, int screenY) {

        score = 0;
        marioLeftX = 0;
        marioLeftY = 0;
        marioRightX = 0;
        marioRightY = 0;
        marioNewYPos = 0;

        coin = new Coins(context, screenX, screenY);
        block = new Blocks(context, screenX, screenY);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        screenWidth = screenX;
        screenHeight = screenY;


        lifeArray = new ArrayList<>(2);
        lifeArray.add(heart);
        lifeArray.add(heart);
        lifeArray.add(heart);

        coinLoc = coin.levelOneCoinLoc(screenX,screenY);
        blockLoc =  block.levelOneBlockLoc(screenX,screenY);
    }

    public void updateCoinPos( int move){
        for(int i = 0; i < coinLoc.size(); i++){
            Rect newPos = coinLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            coinLoc.set(i,newPos);
        }
    }

    public void updateBlockPos( int move){
        for(int i = 0; i < blockLoc.size(); i++){
            Rect newPos = blockLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            blockLoc.set(i,newPos);
        }
    }

    public Bitmap getbackground() {
        return background;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return this.score;
    }

    public ArrayList<Bitmap> getLifeArray() {
        return lifeArray;
    }

    public ArrayList<Rect> getCoinLoc() {
        return coinLoc;
    }

    public ArrayList<Rect> getBlockLoc() {
        return blockLoc;
    }

    public Bitmap getCoinBitmap(){
        return coin.getCoinBitmap();
    }

    public Bitmap getBlockBitmap(){
        return block.getBlockBitmap();
    }

    public void updateMarioVar(float marioXPos, float marioYPos, int marioWidth, int marioHeight){
        this.marioLeftX = marioXPos;
        this.marioLeftY = marioYPos;
        this.marioRightX = marioXPos + marioWidth;
        this.marioRightY = marioYPos + marioHeight;
    }

    public void update(float marioXPos, float marioYPos, int marioWidth, int marioHeight, int backFrame){
        updateMarioVar(marioXPos,marioYPos, marioWidth, marioHeight);
        this.backFrame = backFrame;
        marioHitCoin();
        updateMarioVar(marioXPos,marioYPos, marioWidth, marioHeight);
    }

    public void marioHitCoin(){

        //Removing collected coins
        for(int i = 0; i < coinLoc.size(); i++)
        {
            Rect c = coinLoc.get(i);

            //Mario hits left of coin
            if((marioRightX >= c.left)  && (marioRightX <= c.right) && (marioRightY >= c.top) && (marioLeftY <= c.bottom))
            {
                coinLoc.remove(i);
                score += coin.getValue();
            }

            //Mario hits right of coin
            if((marioLeftX <= c.right) && (marioLeftX >= c.left) && (marioLeftY <= c.bottom) && (marioRightY >= c.top))
            {
                coinLoc.remove(i);
                score += coin.getValue();
            }

            //Mario hits bottom on coin
            if((marioLeftX <= c.right) && (marioRightX >= c.left) && (marioLeftY <= c.bottom) && (marioLeftY >= c.top))
            {
                coinLoc.remove(i);
                score += coin.getValue();
            }

            //Mario hits top of coin
            if((marioLeftX <= c.right) && (marioRightX >= c.left) && (marioRightY >= c.top) && (marioRightY <= c.bottom))
            {
                coinLoc.remove(i);
                score += coin.getValue();
            }


        }
    }


    public boolean marioHitBlock(int marioWidth, int marioHeight){
        boolean output = false;

        for(int i = 0; i < blockLoc.size(); i++)
        {
            Rect b = blockLoc.get(i);

            //Mario hits left of block
            if((marioRightX  >= b.left)  && (marioRightX <= b.right) && (marioRightY >= b.top) && (marioLeftY <= b.bottom))
            {
                output = true;
                marioNewYPos = b.top;
                System.out.println("Mario hits from Left");
            }

            //Mario hits right of block
            if((marioLeftX <= b.right) && (marioLeftX >= b.left) && (marioLeftY <= b.bottom) && (marioRightY >= b.top))
            {
                output = true;
                marioNewYPos = b.top;
                System.out.println("Mario Hits from right");
            }

            //Mario hits bottom on block
            if((marioLeftX <= b.right) && (marioRightX >= b.left) && (marioLeftY <= b.bottom) && (marioLeftY >= b.top))
            {
                output = true;
                marioNewYPos = b.bottom;
                System.out.println("Mario hits from bottom");
            }

            //Mario hits top of block
            if((marioLeftX <= b.right + 50) && (marioRightX >= b.left - 50) && (marioRightY >= b.top) && (marioRightY <= b.bottom))
            {
                output = true;
                marioNewYPos = b.top;
                System.out.println("Mario hits from top");
            }

        }
        return output;
    }

    public float getHitBlockLoc(){ //returns the upper left position of the block
        return marioNewYPos;
    }

    public int getBlockHeight(){
        return block.getBlockHeight();
    }



}

