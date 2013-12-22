package com.zenit.dragndrop;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Tony Alpskog on 2013-12-21.
 */
public class GraphicUnitWidget extends ImageView implements View.OnClickListener, View.OnLongClickListener {

    GraphicUnit gUnit;
    Bitmap originalBitmap;

    public GraphicUnitWidget(Context context) {
        super(context);
    }

    public GraphicUnitWidget(Context context, GraphicUnit graphicUnit) {
        this(context);
        gUnit = graphicUnit;

        int imageResource = R.drawable.ic_lightbulb;
        switch (gUnit.type) {
            case SWITCH:
                imageResource = R.drawable.ic_lightbulb;
                break;
            case DIMMER:
                break;
            case ROOM_HEATER:
                break;
            case VENT:
                imageResource = R.drawable.ic_unit_fan;
                break;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
        originalBitmap = bitmap;
        setImageBitmap(bitmap);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
////        params.addRule(ViewGroup.MarginLayoutParams, true);
////        params.addRule(ViewGroup.LEFT_OF, R.id.);
////        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
////        marginParams.setLayoutDirection(new Layout.Directions(Layoutdi));
//        setLayoutParams(params); //causes layout update
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOnLongClickListener(this);
        setOnClickListener(this);
    }

    public void setOriginalBitmap(Bitmap bitmap) {
        originalBitmap = bitmap;
    }

    @Override
    public boolean onLongClick(View v) {
       Log.i("G-Click", "Long click detected");
       ClipData clipData = ClipData.newPlainText("label","text");
       this.startDrag(clipData, new DragShadow(this), this, 0);
       return false;
    }

    @Override
    public void onClick(View v) {
        Log.i("G-Click", "View status BEFORE = " + (v.isSelected() ? "Selected" : "Not selected"));

        if(!v.isSelected()) {
            v.setSelected(true);
            Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            Rect bounds = getDrawable().getBounds();
            int width = bounds.width();
            int height = bounds.height();
            int bitmapWidth = getDrawable().getIntrinsicWidth();
            int bitmapHeight = getDrawable().getIntrinsicHeight();
            Log.i("Bitmap", "Height = " + bitmapHeight + "   Width = " + bitmapWidth);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);

            Canvas canvas = new Canvas(bitmap);

            canvas.drawCircle(canvas.getHeight()/2, canvas.getWidth()/2, (float) Math.floor(bitmapHeight/2) - Math.round(paint.getStrokeWidth()/2), paint);
            setImageBitmap(bitmap);
        } else {
            setSelected(false);
            setImageBitmap(originalBitmap);
        }
        Log.i("G-Click", "View status AFTER = " + (v.isSelected()? "Selected" : "Not selected"));
    }

//    @Override
//    public boolean onDrag(View v, DragEvent event) {
//        int dragEvent = event.getAction();
//
//        switch (dragEvent) {
//            case DragEvent.ACTION_DRAG_ENTERED:
//                Log.i("DragEvent", "Entered");
//                break;
//            case DragEvent.ACTION_DRAG_ENDED:
//                Log.i("DragEvent", "Ended");
//                break;
//            case DragEvent.ACTION_DRAG_STARTED:
//                ImageView draggedView = (ImageView) event.getLocalState();
//                Log.i("DragEvent", "Started at LAMP = " + draggedView.getX() + "/" + draggedView.getY() + "   EVENT = " + event.getX() + "/" + event.getY());
//                dragXDiff = event.getX() - draggedView.getX();
//                dragYDiff = event.getY() - draggedView.getY();
//                //stop displaying the view where it was before it was dragged
//                draggedView.setVisibility(View.INVISIBLE);
//                break;
//            case DragEvent.ACTION_DROP:
//                ImageView droppedView = (ImageView) event.getLocalState();
//                Log.i("DragEvent", "Dropped at LAMP = " + Math.round(event.getX() + dragXDiff) + "/" + Math.round(event.getY() + dragYDiff) + "   EVENT = " + event.getX() + "/" + event.getY());
//                Log.i("DragEvent", "Drop target at TOP = " + v.getTop() + "   LEFT = " + v.getLeft());
//                droppedView.setX(Math.round(event.getX() + dragXDiff + v.getLeft() - 70));
//                droppedView.setY(Math.round(event.getY() + dragYDiff/* + v.getTop()*/ - 50));
//                break;
//        }
//        return true;
//    }
}
