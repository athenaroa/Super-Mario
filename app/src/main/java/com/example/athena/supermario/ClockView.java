package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
    int color;

    Bitmap regularMario;
    Bitmap superMario;
    Bitmap fireMario;

    Bitmap coin;
    Bitmap mushroom;
    Bitmap fireFlower;
    Bitmap buzzyBeetle;
    Bitmap blooper;
    Bitmap koopaTroopa;
    Bitmap piranhaPlant;

    Bitmap block;
    Bitmap coinBlock;
    Bitmap heart;
    Bitmap back1;




    public ClockView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        color = 0;


        regularMario = BitmapFactory.decodeResource(getResources(),R.drawable.regularmario);
        superMario = BitmapFactory.decodeResource(getResources(),R.drawable.supermario);
        fireMario = BitmapFactory.decodeResource(getResources(),R.drawable.firemario);

        coin = BitmapFactory.decodeResource(getResources(),R.drawable.coin);
        mushroom = BitmapFactory.decodeResource(getResources(),R.drawable.supermushroom);
        fireFlower = BitmapFactory.decodeResource(getResources(),R.drawable.fireflower);
        buzzyBeetle = BitmapFactory.decodeResource(getResources(),R.drawable.buzzybeetle);
        blooper = BitmapFactory.decodeResource(getResources(),R.drawable.blooper);
        koopaTroopa = BitmapFactory.decodeResource(getResources(),R.drawable.koopatroopa);
        piranhaPlant = BitmapFactory.decodeResource(getResources(),R.drawable.piranhaplant);

        block = BitmapFactory.decodeResource(getResources(),R.drawable.block);
        coinBlock = BitmapFactory.decodeResource(getResources(),R.drawable.coinblock);
        heart = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        back1 = BitmapFactory.decodeResource(getResources(),R.drawable.background);



    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Rect dst = new Rect();
        dst.set(0,0,getWidth(),getHeight());
        canvas.drawBitmap(back1,null, dst, null);

        String date =  new SimpleDateFormat("MM/dd/yyy HH.mm.ss").format(new Date());
        canvas.drawText(date, getWidth() / 2, getHeight() / 2, paint);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ClockThread clockThread = new ClockThread(this);
        clockThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("In onTouchEvent");
        if (color == 3) {
            color = 0;
        }

        switch (color) {
            case 0:
                paint.setColor(Color.RED);
                break;
            case 1:
                paint.setColor(Color.GREEN);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                break;
            default:
                paint.setColor(Color.BLACK);
        }


        color++;

        return true;

    }
}

