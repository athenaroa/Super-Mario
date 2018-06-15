package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Enemies {

    Bitmap buzzyBeetle;
    Bitmap koopaTroopa;
    Bitmap Blooper;

    private int buzzyBeetleWidth;
    private int buzzyBeetleHeight;
    private ArrayList<Rect> enemyLoc;
    private ArrayList<Bitmap> enemyBitmap;

    public Enemies(Context context, int screenX, int screenY){
        buzzyBeetle = BitmapFactory.decodeResource(context.getResources(),R.drawable.buzzybeetle);
        buzzyBeetleWidth = buzzyBeetle.getWidth();
        buzzyBeetleHeight = buzzyBeetle.getHeight();
        enemyLoc = new ArrayList<>();
        enemyBitmap =  new ArrayList<>();


    }

    public int getBuzzyBeetleWidth() {
        return buzzyBeetleWidth;
    }

    public int getBuzzyBeetleHeight() {
        return buzzyBeetleHeight;
    }

    public ArrayList<Bitmap> getEnemyBitmap() {
        return enemyBitmap;
    }

    public ArrayList<Rect> levelOneEnemyLoc (int screenX, int screenY){
        Rect enemy1, enemy2, enemy3;

        enemy1 = new Rect(screenX + (screenX/2), screenY - buzzyBeetleHeight,
                (screenX + (screenX/2)) + (buzzyBeetleWidth), screenY - 50);
        /*
        block2 = new Rect(screenX/2 + blockWidth, (screenY/2) + 200,
                (screenX/2) + (2*blockWidth), (screenY/2) + blockHeight + 200);
                */
        enemyLoc.add(enemy1);
        enemyBitmap.add(buzzyBeetle);

        return enemyLoc;
    }

}
