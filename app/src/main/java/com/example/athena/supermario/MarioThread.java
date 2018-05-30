package com.example.athena.supermario;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MarioThread extends Thread {

    MarioView marioView;

    public MarioThread (MarioView cv)
    {
        marioView = cv;
    }

    public void run()
    {
        SurfaceHolder holder;
        Canvas canvas;

        while(true)
        {
            holder = marioView.getHolder();
            canvas = holder.lockCanvas();

            if(canvas != null)
            {
                marioView.draw(canvas);
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
