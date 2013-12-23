package com.zenit.dragndrop;

import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Tony Alpskog on 2013-12-22.
 */
public class TouchListener implements View.OnTouchListener {

    final String TAG = "TouchListener";

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        // Dump touch event to log
//        dumpEvent(event);
        float distx, disty;

        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                //A pressed gesture has started, the motion contains the initial starting location.
//                Log.d(TAG, "ACTION_DOWN");
                touchState = TOUCH;
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
                    if(finalDist >= MIN_PINCH_DISTANCE)
                        Log.d(TAG, "We got a pinch " + (isPinchOut? "OUT": "IN") + "(" + finalDist +")");
                    pinchBeginDist = pinchEndDist = 0;
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

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
                "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_" ).append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid " ).append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")" );
        }
        sb.append("[" );
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#" ).append(i);
            sb.append("(pid " ).append(event.getPointerId(i));
            sb.append(")=" ).append((int) event.getX(i));
            sb.append("," ).append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";" );
        }
        sb.append("]" );
        Log.d(TAG, sb.toString());
    }
}
