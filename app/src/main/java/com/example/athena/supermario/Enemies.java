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
    Bitmap piranhaPlantUP;
    Bitmap piranhaPlantDOWN;

    private int buzzyBeetleWidth;
    private int buzzyBeetleHeight;
    private int piranhaPlantUPWidth;
    private int piranhaPlantUPHeight;
    private int piranhaPlantDOWNWidth;
    private int piranhaPlantDOWNHeight;


    private ArrayList<Rect> enemyLoc;
    private ArrayList<Bitmap> enemyBitmap;

    public Enemies(Context context, int screenX, int screenY){
        buzzyBeetle = BitmapFactory.decodeResource(context.getResources(),R.drawable.buzzybeetle);
        piranhaPlantUP = BitmapFactory.decodeResource(context.getResources(),R.drawable.piranhaplantup);
        piranhaPlantDOWN = BitmapFactory.decodeResource(context.getResources(),R.drawable.mariopipe);

        buzzyBeetleWidth = buzzyBeetle.getWidth();
        buzzyBeetleHeight = buzzyBeetle.getHeight();

        piranhaPlantUPHeight = piranhaPlantUP.getHeight();
        piranhaPlantUPWidth = piranhaPlantUP.getWidth();

        piranhaPlantDOWNWidth = piranhaPlantDOWN.getWidth();
        piranhaPlantDOWNHeight = piranhaPlantDOWN.getHeight();

        enemyLoc = new ArrayList<>();
        enemyBitmap =  new ArrayList<>();
    }

    public void deleteEnemyBitmap(int index){
        enemyBitmap.remove(index);
    }
    public void updateEnemyBitmap(int index, Bitmap update){
        enemyBitmap.set(index,update);
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



        //Frame 2
        enemy1 = new Rect(screenX + (screenX/2), screenY - buzzyBeetleHeight,
                (screenX + (screenX/2)) + (buzzyBeetleWidth), screenY - 50);

        //Frame 3
        enemy2 = new Rect(2*screenX + (screenX/2), screenY - piranhaPlantUPHeight,
                (2*screenX + (screenX/2)) + (piranhaPlantUPWidth), screenY - 50);


        enemyLoc.add(enemy1);
        enemyBitmap.add(buzzyBeetle);

        enemyLoc.add(enemy2);
        enemyBitmap.add(piranhaPlantUP);

        return enemyLoc;
    }

}
