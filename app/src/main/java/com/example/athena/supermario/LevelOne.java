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
    private int score;

    private Coins coin;

    Rect coin1, coin2, coin3,coin4, coin10;


    public LevelOne(Context context, int screenX, int screenY) {

        score = 0;
        marioLeftX = 0;
        marioLeftY = 0;
        marioRightX = 0;
        marioRightY = 0;

        coin = new Coins(context, screenX, screenY);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        screenWidth = screenX;
        screenHeight = screenY;


        lifeArray = new ArrayList<>(2);
        lifeArray.add(heart);
        lifeArray.add(heart);
        lifeArray.add(heart);

        coinLoc = new ArrayList<>();


        //Frame 0
        coin3 = new Rect((screenX/2) -screenX, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                ((screenX/2) + coin.getCoinWidth()) - screenX , screenY - (coin.getCoinHeight()/2));

        coinLoc.add(coin3);

        //Frame 1
        coin1 = new Rect(screenX/2, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                (screenX/2) + coin.getCoinWidth(), screenY - (coin.getCoinHeight()/2));
        coin2 = new Rect(screenX/2 + 100, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                (screenX/2) + coin.getCoinWidth() + 100, screenY - (coin.getCoinHeight()/2));
        coin4 = new Rect( (screenX/2) - 100, (screenY/2),
                (screenX/2) + coin.getCoinWidth() - 100, (screenY/2) +  (coin.getCoinHeight()));

        coinLoc.add(coin1);
        coinLoc.add(coin2);
        coinLoc.add(coin4);


        //Frame 2
        coin10 = new Rect((screenX/2) * 2, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                ((screenX/2) * 2) + coin.getCoinWidth(), screenY - (coin.getCoinHeight()/2));
        coinLoc.add(coin10);



    }

    public void updateCoinPos( int move){
        for(int i = 0; i < coinLoc.size(); i++){
            Rect newPos = coinLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            coinLoc.set(i,newPos);
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

    public Bitmap getCoinBitmap(){
        return coin.getCoinBitmap();
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
        marioHitItem();

    }

    public void marioHitItem(){

        //Removing collected coins
        for(int i = 0; i < coinLoc.size(); i++)
        {
            Rect c = coinLoc.get(i);
            if(((marioRightX >= c.left) && marioLeftX <= c.right)
                    && ((marioLeftY <= c.bottom) && (marioRightY >= c.top )))
            {
                coinLoc.remove(i);
                score += coin.getValue();

            }
        }
    }




}

