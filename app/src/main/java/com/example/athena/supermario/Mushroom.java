package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Mushroom {

    Bitmap mushroom;
    private int mushroomWidth;
    private int mushroomHeight;
    private final int mushroomValue = 1000;

    private ArrayList<Rect> mushroomLoc;

    public Mushroom(Context context, int screenX, int screenY){
        mushroom = BitmapFactory.decodeResource(context.getResources(),R.drawable.supermushroom);
        mushroomWidth = mushroom.getWidth();
        mushroomHeight = mushroom.getHeight();
        mushroomLoc = new ArrayList<>();
    }

    public int getMushroomWidth() {
        return mushroomWidth;
    }

    public int getMushroomHeight() {
        return mushroomHeight;
    }

    public int getValue() {
        return mushroomValue;
    }

    public Bitmap getMushroomBitmap() {
        return mushroom;
    }

    public ArrayList<Rect> levelOneMushroomLoc(int screenX, int screenY){
        Rect mushroom1;

        //Frame 2
        mushroom1 = new Rect(screenX - 100, screenY - (mushroomHeight + mushroomHeight/2),
                (screenX +mushroomWidth) - 100 , screenY - (mushroomWidth/2));
        mushroomLoc.add(mushroom1);

        return mushroomLoc;
    }
}
