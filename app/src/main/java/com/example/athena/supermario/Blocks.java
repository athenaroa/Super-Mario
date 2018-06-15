package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Blocks {

    Bitmap block;
    Bitmap coinBlock;

    private int blockWidth;
    private int blockHeight;
    private ArrayList<Rect> blockLoc;
    private ArrayList<Bitmap> blockBitmap;

    public Blocks(Context context, int screenX, int screenY){
        block = BitmapFactory.decodeResource(context.getResources(),R.drawable.block);
        coinBlock = BitmapFactory.decodeResource(context.getResources(),R.drawable.coinblock);
        blockWidth = block.getWidth();
        blockHeight = block.getHeight();
        blockLoc = new ArrayList<>();
        blockBitmap =  new ArrayList<>();


    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public ArrayList<Bitmap> getBlockBitmap() {
        return blockBitmap;
    }

    public ArrayList<Rect> levelOneBlockLoc (int screenX, int screenY){
        Rect block1, block2, block3;

        block1 = new Rect(screenX/2, (screenY/2) + 200,
                (screenX/2) + (blockWidth), (screenY/2) + blockHeight + 200);
        block2 = new Rect(screenX/2 + blockWidth, (screenY/2) + 200,
                (screenX/2) + (2*blockWidth), (screenY/2) + blockHeight + 200);
        blockLoc.add(block1);
        blockLoc.add(block2);

        blockBitmap.add(coinBlock);
        blockBitmap.add(block);

        return blockLoc;
    }








}
