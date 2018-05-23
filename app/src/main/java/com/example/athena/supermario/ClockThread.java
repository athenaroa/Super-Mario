package com.example.athena.supermario;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ClockThread extends Thread {

    ClockView clockView;

    public ClockThread(ClockView cv)
    {
        clockView = cv;
    }

    public void run()
    {
        SurfaceHolder holder;
        Canvas canvas;

        while(true)
        {
            holder = clockView.getHolder();
            canvas = holder.lockCanvas();

            if(canvas != null)
            {
                clockView.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            try{
                sleep(1000);
            }

            catch (InterruptedException e)
            {
                System.out.println("Exception occured");
            }

        }
    }


}
