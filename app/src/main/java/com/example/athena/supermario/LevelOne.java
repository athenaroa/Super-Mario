package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class LevelOne {

    Bitmap background;
    private PointsAggregator pointsAggregator;
    public int lives;
    Bitmap heart;
    private ArrayList<Bitmap> lifeArray;

    public LevelOne(Context context, int screenX, int screenY){

        pointsAggregator = new PointsAggregator();
        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        heart = BitmapFactory.decodeResource(context.getResources(),R.drawable.heart);
        lifeArray = new ArrayList<>(2);
        lifeArray.add(heart);
        lifeArray.add(heart);
        lifeArray.add(heart);

    }

    public Bitmap getbackground(){
        return background;
    }

    public int getLives(){
        return lives;
    }

    public int getScore(){
        return pointsAggregator.getCurrScore();
    }


    public ArrayList<Bitmap> getLifeArray() {
        return lifeArray;
    }
}
