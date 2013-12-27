package com.zenit.dragndrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Tony Alpskog in 2013.
 */
public class UnitContainerImageView extends ImageView {

    OnContainerBackgroundDrawn mOnContainerBackgroundDrawn;
    private final String TAG = "UnitContainerImageView";

    int scaledBitmapHeight = 0;
    int scaledBitmapWidth = 0;
    int scaledBitmapX = 0;
    int scaledBitmapY = 0;

    public UnitContainerImageView(Context context) {
        super(context);
    }

    public UnitContainerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnitContainerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int oldHeight = scaledBitmapHeight;
        int oldWidth = scaledBitmapWidth;
        int oldX = scaledBitmapX;
        int oldY = scaledBitmapY;

        updateScaledBitmapDimensions();

        if(oldX != scaledBitmapX || oldY != scaledBitmapY || oldHeight != scaledBitmapHeight || oldWidth != scaledBitmapWidth) {
            Log.e("Room", "width=" + getScaledBitmapWidth() + " height="+getScaledBitmapHeight() + " x=" + getScaledBitmapX() + " y=" + getScaledBitmapY());
            postOnContainerBackgroundDrawn();
        }
    }

    private void updateScaledBitmapDimensions() {

        // Get image matrix values and place them in an array
        float[] f = new float[9];
        getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];
        scaledBitmapX = Math.round(f[Matrix.MTRANS_X]);
        scaledBitmapY = Math.round(f[Matrix.MTRANS_Y]);

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        scaledBitmapWidth = Math.round(origW * scaleX);
        scaledBitmapHeight = Math.round(origH * scaleY);
    }

    public int getScaledBitmapHeight() {
        return scaledBitmapHeight;
    }

    public int getScaledBitmapWidth() {
        return scaledBitmapWidth;
    }

    public int getScaledBitmapX() {
        return scaledBitmapX;
    }

    public int getScaledBitmapY() {
        return scaledBitmapY;
    }

    public interface OnContainerBackgroundDrawn {
        boolean onContainerBackgroundDrawn(View v);
    }

    public void setOnContainerBackgroundDrawnListener(OnContainerBackgroundDrawn eventListener) {
        mOnContainerBackgroundDrawn = eventListener;
    }

    private boolean postOnContainerBackgroundDrawn() {
        if(mOnContainerBackgroundDrawn != null) {
            mOnContainerBackgroundDrawn.onContainerBackgroundDrawn(this);
            return true;
        } else Log.w(TAG, "Cannot post event. OnContainerBackgroundDrawn is NULL");
        return false;
    }
}
