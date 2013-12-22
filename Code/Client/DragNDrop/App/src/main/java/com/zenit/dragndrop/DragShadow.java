package com.zenit.dragndrop;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

/**
 * Created by Tony Alpskog on 2013-12-22.
 */
//Use DragShadow to modify the object shown when dragged.
public class DragShadow extends View.DragShadowBuilder {

    int shadowDiameter = 200;

    public DragShadow(View v) {
        super(v);

    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        super.onDrawShadow(canvas);

        int strokeWidth = 4;

        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setAlpha(50);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setMaskFilter(new BlurMaskFilter(shadowDiameter/4, BlurMaskFilter.Blur.INNER));

        canvas.drawCircle(shadowDiameter/2, shadowDiameter/2, shadowDiameter/2, circlePaint);

    }
    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point touchPoint) {
        Log.i("touchPoint before", touchPoint.x + "/" + touchPoint.y);
        shadowSize.set(shadowDiameter, shadowDiameter);
        touchPoint.set(/*shadowDiameter/6 - getView().getWidth()*/shadowDiameter/2, /*shadowDiameter/6 - getView().getHeight()*/shadowDiameter/2);
        Log.i("touchPoint after", touchPoint.x + "/" + touchPoint.y);
    }
//            int centerOffset = 52;
//            int width, height;
//
//            public DragShadow(View baseView)
//            {
//                super(baseView);
//            }
//
//            @Override
//            public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)
//            {
//                width = getView().getWidth();
//                height = getView().getHeight();
//                // This is the overall dimension of your drag shadow
//                shadowSize.set(width * 2, height * 2);
//                // This one tells the system how to translate your shadow on the screen so
//                // that the user fingertip is situated on that point of your canvas.
//                // In my case, the touch point is in the middle of the (height, width) top-right rect
//                shadowTouchPoint.set(width + width / 2 - centerOffset, height / 2 + centerOffset);
//            }
//
//            @Override
//            public void onDrawShadow(Canvas canvas)
//            {
//                float sepAngle = (float)Math.PI / 16;
//                float circleRadius = 2f;
//                // Draw the shadow circles in the top-right corner
//                float centerX = width + width / 2 - centerOffset;
//                float centerY = height / 2 + centerOffset;
//                int baseColor = Color.BLACK;
//                Paint paint = new Paint();
//                paint.setAntiAlias(true);
//                paint.setColor(baseColor);
//
//                // draw a dot where the center of the touch point (i.e. your fingertip) is
//                canvas.drawCircle(centerX, centerY, circleRadius + 1, paint);
//                for (int radOffset = 70; centerX + radOffset < canvas.getWidth(); radOffset += 20) {
//                    // Vary the alpha channel based on how far the dot is
//                    baseColor = Color.alpha((byte)(128 * (2f * (width / 2f - 1.3f * radOffset + 60) / width) + 100));
//                    paint.setColor(baseColor);
//                    // Draw the dots along a circle of radius radOffset and centered on centerX,centerY
//                    for (float angle = 0; angle < Math.PI * 2; angle += sepAngle) {
//                        float pointX = centerX + (float)Math.cos(angle) * radOffset;
//                        float pointY = centerY + (float)Math.sin(angle) * radOffset;
//                        canvas.drawCircle(pointX, pointY, circleRadius, paint);
//                    }
//                }
//                // Draw the dragged view in the bottom-left corner
//                canvas.drawBitmap(getView().getDrawingCache(), 0, height, null);
//            }
}

