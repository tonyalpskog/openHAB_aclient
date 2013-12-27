package com.zenit.dragndrop;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tony Alpskog in 2013.
 */
public class DropTargetImageView extends ImageView {

    OnDragTargetUpdate mOnDragTargetUpdate;
    private final String TAG = "DropTargetImageView";

    public DropTargetImageView(Context context) {
        super(context);
    }

    public DropTargetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropTargetImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postOnDragTargetUpdate();
    }

    public interface OnDragTargetUpdate {
        boolean onDragTargetUpdate(View v);
    }

    public void setOnDragTargetUpdate(OnDragTargetUpdate eventListener) {
        mOnDragTargetUpdate = eventListener;
    }

    private boolean postOnDragTargetUpdate() {
        if(mOnDragTargetUpdate != null) {
            mOnDragTargetUpdate.onDragTargetUpdate(this);
            return true;
        } else Log.w(TAG, "Cannot post event. OnDragTargetUpdate is NULL");
        return false;
    }
}
