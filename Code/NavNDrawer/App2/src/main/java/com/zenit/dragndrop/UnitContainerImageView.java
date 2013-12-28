package com.zenit.dragndrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zenit.navndrawer.HABApplication;
import com.zenit.navndrawer.R;
import com.zenit.navndrawer.Room;

import java.util.Iterator;

/**
 * Created by Tony Alpskog in 2013.
 */
public class UnitContainerImageView extends ImageView {

    OnContainerBackgroundDrawn mOnContainerBackgroundDrawn;
    private final String TAG = "UnitContainerImageView";

    private int scaledBitmapHeight = 0;
    private int scaledBitmapWidth = 0;
    private int scaledBitmapX = 0;
    private int scaledBitmapY = 0;

    private Room room;

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

            redrawAllUnits();
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
        }
        return false;
    }

    private void redrawAllUnits() {
        Iterator unitIterator = room.getUnitIterator();
        GraphicUnit graphicUnit;
        while (unitIterator.hasNext()) {
            graphicUnit = (GraphicUnit) unitIterator.next();
            graphicUnit.resetView();
            drawUnitInRoom(graphicUnit);
        }
    }

    private void drawUnitInRoom(GraphicUnit gUnit) {
        int x = Math.round(getScaledBitmapX() + (getScaledBitmapWidth() / gUnit.getRoomRelativeX()));
        int y = Math.round(getScaledBitmapY() + (getScaledBitmapHeight() / gUnit.getRoomRelativeY()));
        drawUnitInRoom(gUnit, x, y);
    }

    public void addNewUnitToRoom(GraphicUnit gUnit, int x, int y) {
        room.addUnit(gUnit);
        drawUnitInRoom(gUnit, x, y);
    }

    private void drawUnitInRoom(GraphicUnit gUnit, int x, int y) {
        ViewGroup layout = (ViewGroup) getParent();///*fragmentView.*/findViewById(R.id.room_layout);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//        FrameLayout layout = (FrameLayout) /*fragmentView.*/findViewById(R.id.room_layout);

//        Log.d("UnitPos", params.leftMargin + "/" + params.topMargin);
        Log.d("Unit", "drawUnitInRoom() view group x/y = " + layout.getX() + "/" + layout.getY() + "    room x/y = " + getScaledBitmapX() + "/" + getScaledBitmapY());
        View gView = gUnit.getView(/*fragmentView.*/getContext());
        layout.addView(gView, x, y);
//        layout.addView(gView, params);
        Log.d("Unit", "drawUnitInRoom() type=" + gUnit.getType().name() + "   in x/y = " + x + "/" + y + "   out x/y = " + gView.getX() + "/" + gView.getY());
    }

    public void setRoom(Room nextRoom) {
        room = nextRoom;
    }

    public Room getRoom() {
        return room;
    }
}
