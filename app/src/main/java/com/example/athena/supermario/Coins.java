package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Coins extends Item {

    Bitmap coin;
    private int coinWidth;
    private int coinHeight;


    public Coins(Context context, int screenX, int screenY){
        coin = BitmapFactory.decodeResource(context.getResources(),R.drawable.coin);
        coinWidth = coin.getWidth();
        coinHeight = coin.getHeight();
    }

    public int getCoinWidth() {
        return coinWidth;
    }

    public int getCoinHeight() {
        return coinHeight;
    }

    @Override
    public int getValue() {
        return super.getValue();
    }

    @Override
    public int getPoints(SuperMarioVisitor visitor) {
        return super.getPoints(visitor);
    }

    public Bitmap getCoinBitmap() {
        return coin;
    }
}
