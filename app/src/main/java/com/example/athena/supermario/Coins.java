package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class Coins {

    Bitmap coin;
    private int coinWidth;
    private int coinHeight;
    private final int coinValue = 200;

    private ArrayList<Rect> coinLoc;

    public Coins(Context context, int screenX, int screenY){
        coin = BitmapFactory.decodeResource(context.getResources(),R.drawable.coin);
        coinWidth = coin.getWidth();
        coinHeight = coin.getHeight();
        coinLoc = new ArrayList<>();
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


    public ArrayList<Rect> levelOneCoinLoc(int screenX, int screenY){
        Rect coin1,coin2,coin3,coin4,coin10;

        //Frame 0
        coin1 = new Rect((screenX/2) -screenX, screenY - (coinHeight + coinHeight/2),
                ((screenX/2) + coinWidth) - screenX , screenY - (coinHeight/2));

        this.coinLoc.add(coin1);

        //Frame 1
        coin2 = new Rect(screenX/2, screenY - (coinHeight + coinHeight/2),
                (screenX/2) + coinWidth, screenY - (coinWidth/2));
        coin3 = new Rect(screenX/2 + 100, screenY - (coinHeight + coinHeight/2),
                (screenX/2) + coinWidth + 100, screenY - (coinHeight/2));
        coin4 = new Rect( (screenX/2) - 100, (screenY/2),
                (screenX/2) + coinWidth - 100, (screenY/2) +  (coinHeight));

        this.coinLoc.add(coin2);
        this.coinLoc.add(coin3);
        this.coinLoc.add(coin4);


        //Frame 2
        coin10 = new Rect((screenX/2) * 2, screenY - (coinHeight + coinHeight/2),
                ((screenX/2) * 2) + coinWidth, screenY - (coinHeight/2));
        coinLoc.add(coin10);

        return coinLoc;
    }
}
