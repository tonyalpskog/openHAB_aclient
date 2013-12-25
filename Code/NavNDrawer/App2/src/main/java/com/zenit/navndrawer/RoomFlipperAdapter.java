package com.zenit.navndrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Tony Alpskog on 2013-12-24.
 */
public class RoomFlipperAdapter {

    private final String TAG = "RoomFlipperAdapter";
    private int currentImageIndex = 0;
    private Bitmap[] bitmapList = null;
    private Context mContext = null;

    public RoomFlipperAdapter(Context context) {
        mContext = context;

        bitmapList = new Bitmap[] {
            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img1),
            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img2),
            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img3),
            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img4),
            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img5),
        };
    }

    private int getNextIndex() {
        currentImageIndex = (currentImageIndex == bitmapList.length - 1? 0 : currentImageIndex + 1);
        return currentImageIndex;
    }

    private int getPrevIndex() {
        currentImageIndex = (currentImageIndex == 0? bitmapList.length - 1 : currentImageIndex - 1);
        return currentImageIndex;
    }

    public Bitmap getBitmap(Gesture gesture) {
        Bitmap nextBitmap = null;
        switch(gesture) {
            case PINCH_OUT:
                Log.d(TAG, "Pinch to LOWER view");
                nextBitmap = bitmapList[getPrevIndex()];
                break;

            case PINCH_IN:
                Log.d(TAG, "Pinch to UPPER view");
                nextBitmap = bitmapList[getNextIndex()];
                break;

            case SWIPE_LEFT:
                //Swipe to RIGHT view
                Log.d(TAG, "Swipe to RIGHT view");
                nextBitmap = bitmapList[getNextIndex()];
                break;

            case SWIPE_RIGHT:
                //Swipe to LEFT view
                Log.d(TAG, "Swipe to LEFT view");
                nextBitmap = bitmapList[getPrevIndex()];
                break;

            case SWIPE_UP:
                //Swipe to LOWER view
                Log.d(TAG, "Swipe to LOWER view");
                nextBitmap = bitmapList[getPrevIndex()];
                break;

            case SWIPE_DOWN:
                //Swipe to UPPER view
                Log.d(TAG, "Swipe to UPPER view");
                nextBitmap = bitmapList[getNextIndex()];
                break;
        }
        return nextBitmap;
    }
}
