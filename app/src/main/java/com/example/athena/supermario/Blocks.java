package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Blocks {

    Bitmap block;
    private int blockWidth;
    private int blockHeight;

    public Blocks(Context context, int screenX, int screenY){
        block = BitmapFactory.decodeResource(context.getResources(),R.drawable.block);
        blockWidth = block.getWidth();
        blockHeight = block.getHeight();

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





}
