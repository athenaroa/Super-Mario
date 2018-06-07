package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Coins {

    Bitmap coin;
    private int coinWidth;
    private int coinHeight;
    private final int coinValue = 200;


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

    public int getValue() {
        return coinValue;
    }

    public Bitmap getCoinBitmap() {
        return coin;
    }
}
