package com.zenit.navndrawer;

import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * Created by Tony Alpskog on 2013-12-22.
 */
//TODO - Have a closer look at GestureDetector.OnGestureListener and ScaleGestureDetector.OnScaleGestureListener
//TODO - Will those interfaces make this code better?
public class TouchListener implements View.OnTouchListener {

    final String TAG = "TouchListener";
    final String TAG2 = "Navigation";

    final float MIN_PINCH_DISTANCE = 100;
    //Touch event related variables
    final int IDLE = 0;
    final int TOUCH = 1;
    final int PINCH = 2;
    int touchState = IDLE;
    float dist0 = 1, distCurrent = 1;

    float pinchBeginDist = 0;
    float pinchEndDist = 0;
    boolean isPinchOut = false;
    boolean ongoingPinch = false;
    private float initialX;
    private float initialY;

    private ViewFlipper touchViewFlipper;

    public TouchListener(ViewFlipper viewFlipper) {
        touchViewFlipper = viewFlipper;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, MotionEvent.axisToString(event.getAction()));

//        // Dump touch event to log
//        dumpEvent(event);
        float distx, disty;

        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                //A pressed gesture has started, the motion contains the initial starting location.
//                Log.d(TAG, "ACTION_DOWN");
                touchState = TOUCH;
                initialX = event.getX();
                initialY = event.getY();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                //A non-primary pointer has gone down.
//                Log.d(TAG, "ACTION_POINTER_DOWN");
                touchState = PINCH;

                //Get the distance when the second pointer touch
                distx = event.getX(0) - event.getX(1);
                disty = event.getY(0) - event.getY(1);
                dist0 = FloatMath.sqrt(distx * distx + disty * disty);

                pinchBeginDist = dist0;
                ongoingPinch = true;

                break;

            case MotionEvent.ACTION_MOVE:
                //A change has happened during a press gesture (between ACTION_DOWN and ACTION_UP).
//                Log.d(TAG, "ACTION_MOVE");

                if(touchState == PINCH){
                    //Get the current distance
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    distCurrent = FloatMath.sqrt(distx * distx + disty * disty);

                    if(pinchEndDist == 0)//2:nd measure will detect the type of pinch.
                        isPinchOut = pinchBeginDist < distCurrent;
                    else {
                        if(isPinchOut && pinchEndDist > distCurrent) {
                            //Pinch is being reversed from OUT to IN
                            pinchBeginDist = pinchEndDist;
                            isPinchOut = false;
                        } else if(!isPinchOut && pinchEndDist < distCurrent) {
                            //Pinch is being reversed from IN to OUT
                            pinchBeginDist = pinchEndDist;
                            isPinchOut = true;
                        }

                    }
                    pinchEndDist = distCurrent;

//                    Log.d(TAG, "Pinching = " + distCurrent);
                }

                break;

            case MotionEvent.ACTION_UP:
                //A pressed gesture has finished.
//                Log.d(TAG, "ACTION_UP");
                touchState = IDLE;
                if(ongoingPinch) {
                    ongoingPinch = false;
                    float finalDist = isPinchOut? pinchEndDist - pinchBeginDist: pinchBeginDist - pinchEndDist;
                    if(finalDist >= MIN_PINCH_DISTANCE) {
                        Log.d(TAG, "We got a pinch " + (isPinchOut? "OUT": "IN") + "(" + finalDist +")");

                        if(isPinchOut) {
                            Log.d(TAG2, "Pinch to LOWER view");
                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), android.R.anim.fade_in);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), android.R.anim.fade_out);
                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == 0? touchViewFlipper.getChildCount() - 1: touchViewFlipper.getDisplayedChild() - 1);
                        } else {
                            Log.d(TAG2, "Pinch to UPPER view");
                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), android.R.anim.fade_in);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), android.R.anim.fade_out);
                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == touchViewFlipper.getChildCount() - 1? 0: touchViewFlipper.getDisplayedChild() + 1);
                        }
                    }
                    pinchBeginDist = pinchEndDist = 0;
                } else {
                    Log.d(TAG, "Inside touchViewFlipper MotionEvent.ACTION_UP code");

                    float finalX = event.getX();
                    float finalY = event.getY();

                    if(Math.abs(initialX - finalX) > Math.abs(initialY - finalY)) {
                        //Horizontal swipe
                        if (initialX > finalX) {
                            //Swipe to RIGHT view
                            Log.d(TAG2, "Swipe to RIGHT view");

                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), R.anim.in_right);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), R.anim.out_left);

                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == touchViewFlipper.getChildCount() - 1? 0: touchViewFlipper.getDisplayedChild() + 1);
                        } else {
                            //Swipe to LEFT view
                            Log.d(TAG2, "Swipe to LEFT view");

                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), R.anim.in_left);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), R.anim.out_right);

                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == 0? touchViewFlipper.getChildCount() - 1: touchViewFlipper.getDisplayedChild() - 1);
                        }
                    } else {
                        //Vertical swipe
                        if (initialY > finalY) {
                            //Swipe to LOWER view
                            Log.d(TAG2, "Swipe to LOWER view");

                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), R.anim.in_down);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), R.anim.out_up);

                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == 0? touchViewFlipper.getChildCount() - 1: touchViewFlipper.getDisplayedChild() - 1);
                        } else {
                            //Swipe to UPPER view
                            Log.d(TAG2, "Swipe to UPPER view");

                            touchViewFlipper.setInAnimation(touchViewFlipper.getContext(), R.anim.in_up);
                            touchViewFlipper.setOutAnimation(touchViewFlipper.getContext(), R.anim.out_down);

                            touchViewFlipper.setDisplayedChild(touchViewFlipper.getDisplayedChild() == touchViewFlipper.getChildCount() - 1? 0: touchViewFlipper.getDisplayedChild() + 1);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //A non-primary pointer has gone up.
//                Log.d(TAG, "ACTION_POINTER_UP");
                touchState = TOUCH;
                break;
        }

        return true;
    }

//    /** Show an event in the LogCat view, for debugging */
//    private void dumpEvent(MotionEvent event) {
//        String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
//                "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
//        StringBuilder sb = new StringBuilder();
//        int action = event.getAction();
//        int actionCode = action & MotionEvent.ACTION_MASK;
//        sb.append("event ACTION_" ).append(names[actionCode]);
//        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
//                || actionCode == MotionEvent.ACTION_POINTER_UP) {
//            sb.append("(pid " ).append(
//                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
//            sb.append(")" );
//        }
//        sb.append("[" );
//        for (int i = 0; i < event.getPointerCount(); i++) {
//            sb.append("#" ).append(i);
//            sb.append("(pid " ).append(event.getPointerId(i));
//            sb.append(")=" ).append((int) event.getX(i));
//            sb.append("," ).append((int) event.getY(i));
//            if (i + 1 < event.getPointerCount())
//                sb.append(";" );
//        }
//        sb.append("]" );
//        Log.d(TAG, sb.toString());
//    }
}
