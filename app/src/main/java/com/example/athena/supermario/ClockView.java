package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint;
    int color;

    public ClockView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        color = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

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

