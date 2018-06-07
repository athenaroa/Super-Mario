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


    private PointsAggregator pointsAggregator;
    private ArrayList<Bitmap> lifeArray;
    private ArrayList<Rect> coinLoc;

    private Coins coin;

    Rect coin1, coin2, coin3;


    public LevelOne(Context context, int screenX, int screenY) {

        pointsAggregator = new PointsAggregator();
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

        //Frame 1
        coin1 = new Rect(screenX/2, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                (screenX/2) + coin.getCoinWidth(), screenY - (coin.getCoinHeight()/2));
        coin2 = new Rect(screenX/2 + 100, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                (screenX/2) + coin.getCoinWidth() + 100, screenY - (coin.getCoinHeight()/2));
        coinLoc.add(coin1);
        coinLoc.add(coin2);


        //Frame 2
        coin3 = new Rect((screenX/2) * 2, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                ((screenX/2) * 2) + coin.getCoinWidth(), screenY - (coin.getCoinHeight()/2));
        coinLoc.add(coin3);


        marioLeftX = 0;
        marioLeftY = 0;
        marioRightX = 0;
        marioRightY = 0;





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
        return pointsAggregator.getCurrScore();
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
        //returns 0 if none
        //returns 1 if coin
        //returns 2 if super mushroom
        //returns 3 if fire flower

        //Checking if it hit coins
        for(int i = 0; i < coinLoc.size(); i++)
        {
            //Mario going forward into coin
            Rect coin = coinLoc.get(i);

            System.out.println("Coin number: " + i);
            System.out.println("marioLeftX = " + marioLeftX);
            System.out.println("marioRightX = " + marioRightX);
            System.out.println("Coin.left = " + coin.left);
            System.out.println("Coin.right = " + coin.right);



            if(marioRightX > coin.left)
            {
                coinLoc.remove(i);
            }


        }
        //return 0;
    }




}

