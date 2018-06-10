package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class FireFlower {

    Bitmap fireFlower;
    private int flowerWidth;
    private int flowerHeight;
    private final int flowerValue = 1000;

    private ArrayList<Rect> flowerLoc;

    public FireFlower(Context context, int screenX, int screenY){
        fireFlower = BitmapFactory.decodeResource(context.getResources(),R.drawable.fireflower);
        flowerWidth = fireFlower.getWidth();
        flowerHeight = fireFlower.getHeight();
        flowerLoc = new ArrayList<>();
    }

    public int getFlowerWidth() {
        return flowerWidth;
    }

    public int getFlowerHeight() {
        return flowerHeight;
    }

    public int getValue() {
        return flowerValue;
    }

    public Bitmap getFlowerBitmap() {
        return fireFlower;
    }

    public ArrayList<Rect> levelOneFlowerLoc(int screenX, int screenY){
        Rect flower1;

        //Frame 2
        flower1 = new Rect(screenX - 300, screenY - (flowerHeight + flowerHeight/2),
                (screenX +flowerWidth) - 300 , screenY - (flowerWidth/2));
        flowerLoc.add(flower1);

        return flowerLoc;
    }
}
