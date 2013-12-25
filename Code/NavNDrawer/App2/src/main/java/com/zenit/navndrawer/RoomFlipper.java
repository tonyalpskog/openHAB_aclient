package com.zenit.navndrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * Created by Tony Alpskog in 2013.
 */
public class RoomFlipper extends ViewFlipper implements GestureListener.OnGestureListener {

    final String TAG = "RoomFlipper";

    OnRoomShiftListener mOnRoomShiftListener;
    GestureListener mGestureListener;
    RoomFlipperAdapter mRoomFlipperAdapter;
    ImageView[] flipperImages;

    public RoomFlipper(Context context) {
        super(context);
    }

    public RoomFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGestureListener(GestureListener gestureListener) {
        mGestureListener = gestureListener;
        setOnTouchListener(mGestureListener);
        mGestureListener.setOnGestureListener(this);
    }

    @Override
    public boolean onGesture(View v, Gesture gesture) {
        int nextChildIndex = getDisplayedChild();
        boolean doBounce = true;
        boolean gestureFound = true;

        Bitmap nextBitmap = mRoomFlipperAdapter.getBitmap(gesture);
        if(nextBitmap != null) {
            doBounce = false;
            nextChildIndex = setNextImage(nextBitmap);
        }

        switch(gesture) {
            case PINCH_OUT:
                Log.d(TAG, "Pinch to LOWER view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_pinch);
                    setOutAnimation(getContext(), R.anim.out_bounce_pinch);
                } else {
                    setInAnimation(getContext(), android.R.anim.fade_in);
                    setOutAnimation(getContext(), android.R.anim.fade_out);
                }
                break;

            case PINCH_IN:
                Log.d(TAG, "Pinch to UPPER view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_pinch);
                    setOutAnimation(getContext(), R.anim.out_bounce_pinch);
                } else {
                    setInAnimation(getContext(), android.R.anim.fade_in);
                    setOutAnimation(getContext(), android.R.anim.fade_out);
                }
                break;

            case SWIPE_LEFT:
                //Swipe to RIGHT view
                Log.d(TAG, "Swipe to RIGHT view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_left);
                    setOutAnimation(getContext(), R.anim.out_bounce_left);
                } else {
                    setInAnimation(getContext(), R.anim.in_right);
                    setOutAnimation(getContext(), R.anim.out_left);
                }
                break;

            case SWIPE_RIGHT:
                //Swipe to LEFT view
                Log.d(TAG, "Swipe to LEFT view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_right);
                    setOutAnimation(getContext(), R.anim.out_bounce_right);
                } else {
                    setInAnimation(getContext(), R.anim.in_left);
                    setOutAnimation(getContext(), R.anim.out_right);
                }
                break;

            case SWIPE_UP:
                //Swipe to LOWER view
                Log.d(TAG, "Swipe to LOWER view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_up);
                    setOutAnimation(getContext(), R.anim.out_bounce_up);
                } else {
                    setInAnimation(getContext(), R.anim.in_down);
                    setOutAnimation(getContext(), R.anim.out_up);
                }
                break;

            case SWIPE_DOWN:
                //Swipe to UPPER view
                Log.d(TAG, "Swipe to UPPER view");
                if(doBounce) {
                    setInAnimation(getContext(), R.anim.in_bounce_down);
                    setOutAnimation(getContext(), R.anim.out_bounce_down);
                } else {
                    setInAnimation(getContext(), R.anim.in_up);
                    setOutAnimation(getContext(), R.anim.out_down);
                }

                break;
            default:
                gestureFound = false;
                break;
        }

        if(gestureFound) {
            setDisplayedChild(nextChildIndex);
            if(!doBounce)
                postOnRoomShift(gesture);
        }

        return true;
    }

    /**
     *
     * @param bitmap The bitmap that will represent the next image
     * @return the child index number for the next image.
     */
    public int setNextImage(Bitmap bitmap) {
        int childIndex = getNextChildIndex();

        flipperImages[childIndex].setImageBitmap(bitmap);
        return childIndex;
    }

    /**
     *
     * @return the child index number for the next image.
     */
    public int getNextChildIndex() {
        int childIndex = 0;

        if(getDisplayedChild() == 0)
            childIndex = 1;

        return childIndex;
    }

    /**
     * Interface definition for a callback to be invoked when another room is shown.
     * The callback will be invoked after the new room is shown.
     */
    public interface OnRoomShiftListener {
        /**
         * Called when another room is shown.
         *
         * @param newView The current view after switch.
         * @param oldView The previous view after switch.
         * @return True if the listener has consumed the event, false otherwise.
         */
        boolean onRoomShift(Gesture gesture);
    }

    private boolean postOnRoomShift(Gesture gesture) {
        if(mOnRoomShiftListener != null) {
            mOnRoomShiftListener.onRoomShift(gesture);
            return true;
        } else Log.w(TAG, "Cannot post event. OnRoomShiftListener is NULL");
        return false;
    }

    public void setOnRoomShiftListener(OnRoomShiftListener eventListener) {
        mOnRoomShiftListener = eventListener;
    }

    public void setRoomFlipperAdapter(RoomFlipperAdapter flipperAdapter) {
        mRoomFlipperAdapter = flipperAdapter;
        flipperImages = new ImageView[2];
        flipperImages[0] = (ImageView) findViewById(R.id.flipper_image_1);
        flipperImages[1] = (ImageView) findViewById(R.id.flipper_image_2);
        flipperImages[getDisplayedChild()].setImageBitmap(mRoomFlipperAdapter.getCurrentBitmap());
    }
}