package com.example.esgameonview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;


public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true;
    private Paint backgroundPaint = new Paint();

    private Bitmap bitmap;
    private int towardPointX;
    private int towardPointY;

    {
        backgroundPaint.setColor(Color.argb(100, 218, 81, 152));
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    public void setTowardPoint(int x, int y) {
        towardPointX = x;
        towardPointY = y;
    }

    @Override
    public void run() {
        int starX = 0;
        int starY = 0;
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    canvas.drawBitmap(bitmap, starX, starY, backgroundPaint);
                    if (starX + bitmap.getWidth() / 2 < towardPointX) starX += 10;
                    if (starX + bitmap.getWidth() / 2 > towardPointX) starX -= 10;
                    if (starY + bitmap.getHeight() / 2 < towardPointY) starY += 10;
                    if (starY + bitmap.getHeight() / 2 > towardPointY) starY -= 10;

                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }
}