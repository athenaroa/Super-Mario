package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Blocks {

    Bitmap block;
    private int blockWidth;
    private int blockHeight;
    private ArrayList<Rect> blockLoc;

    public Blocks(Context context, int screenX, int screenY){
        block = BitmapFactory.decodeResource(context.getResources(),R.drawable.block);
        blockWidth = block.getWidth();
        blockHeight = block.getHeight();
        blockLoc = new ArrayList<>();

    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public Bitmap getBlockBitmap() {
        return block;
    }


    public ArrayList<Rect> levelOneBlockLoc (int screenX, int screenY){
        Rect block1, block2, block3;

        block1 = new Rect(screenX/2, screenY/2,
                (screenX/2) + blockWidth, (screenY/2) + blockHeight);
        blockLoc.add(block1);

        return blockLoc;
    }





}
