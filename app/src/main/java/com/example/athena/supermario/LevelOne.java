package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class LevelOne {

    Bitmap background;
    public int lives;
    Bitmap heart;



    private PointsAggregator pointsAggregator;
    private ArrayList<Bitmap> lifeArray;
    private ArrayList<Rect> coinLoc;

    private Coins coin;

    Rect coin1;


    public LevelOne(Context context, int screenX, int screenY) {

        pointsAggregator = new PointsAggregator();
        coin = new Coins(context, screenX, screenY);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        lifeArray = new ArrayList<>(2);
        lifeArray.add(heart);
        lifeArray.add(heart);
        lifeArray.add(heart);

        coinLoc = new ArrayList<>();
        coin1 = new Rect(screenX/2, screenY - (coin.getCoinHeight() + coin.getCoinHeight()/2),
                (screenX/2) + coin.getCoinWidth(), screenY - (coin.getCoinHeight()/2));
        coinLoc.add(coin1);

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
}

