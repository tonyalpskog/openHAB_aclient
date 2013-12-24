package com.zenit.navndrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * Created by Tony Alpskog on 2013-12-24.
 */
public class RoomFlipper extends ViewFlipper implements GestureListener.OnGestureListener {

    final String TAG = "RoomFlipper";

    OnRoomShiftListener mOnRoomShiftListener;
    GestureListener mGestureListener;

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
        int currentChild = getDisplayedChild();
        switch(gesture) {
            case PINCH_OUT:
                Log.d(TAG, "Pinch to LOWER view");
                setInAnimation(getContext(), android.R.anim.fade_in);
                setOutAnimation(getContext(), android.R.anim.fade_out);
                setDisplayedChild(getDisplayedChild() == 0 ? getChildCount() - 1 : getDisplayedChild() - 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;

            case PINCH_IN:
                Log.d(TAG, "Pinch to UPPER view");
                setInAnimation(getContext(), android.R.anim.fade_in);
                setOutAnimation(getContext(), android.R.anim.fade_out);
                setDisplayedChild(getDisplayedChild() == getChildCount() - 1? 0: getDisplayedChild() + 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;

            case SWIPE_LEFT:
                //Swipe to RIGHT view
                Log.d(TAG, "Swipe to RIGHT view");
                setInAnimation(getContext(), R.anim.in_right);
                setOutAnimation(getContext(), R.anim.out_left);
                setDisplayedChild(getDisplayedChild() == getChildCount() - 1 ? 0 : getDisplayedChild() + 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;

            case SWIPE_RIGHT:
                //Swipe to LEFT view
                Log.d(TAG, "Swipe to LEFT view");
                setInAnimation(getContext(), R.anim.in_left);
                setOutAnimation(getContext(), R.anim.out_right);
                setDisplayedChild(getDisplayedChild() == 0 ? getChildCount() - 1 : getDisplayedChild() - 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;

            case SWIPE_UP:
                //Swipe to LOWER view
                Log.d(TAG, "Swipe to LOWER view");
                setInAnimation(getContext(), R.anim.in_down);
                setOutAnimation(getContext(), R.anim.out_up);
                setDisplayedChild(getDisplayedChild() == 0 ? getChildCount() - 1 : getDisplayedChild() - 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;

            case SWIPE_DOWN:
                //Swipe to UPPER view
                Log.d(TAG, "Swipe to UPPER view");
                setInAnimation(getContext(), R.anim.in_up);
                setOutAnimation(getContext(), R.anim.out_down);
                setDisplayedChild(getDisplayedChild() == getChildCount() - 1 ? 0 : getDisplayedChild() + 1);
                postOnRoomShift(getDisplayedChild(), currentChild);
                break;
        }
        return true;
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
        boolean onRoomShift(int newView, int oldView);
    }

    private boolean postOnRoomShift(int newView, int oldView) {
        if(mOnRoomShiftListener != null) {
            mOnRoomShiftListener.onRoomShift(newView, oldView);
            return true;
        } else Log.w(TAG, "Cannot post event. OnRoomShiftListener is NULL");
        return false;
    }

    public void setOnRoomShiftListener(OnRoomShiftListener eventListener) {
        mOnRoomShiftListener = eventListener;
    }
}
