package com.zenit.dragndrop;

/**
 * Created by Tony Alpskog on 2013-12-22.
 */

import android.app.Fragment;
import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoomConfigFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private boolean doSelectAll = true;
    private HashMap<UUID, GraphicUnit> unitHash;
    private List<ImageView> unitList;
    private View fragmentView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RoomConfigFragment newInstance(int sectionNumber) {
        RoomConfigFragment fragment = new RoomConfigFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomConfigFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        unitHash = new HashMap<UUID, GraphicUnit>();
        unitList = new ArrayList<ImageView>();

        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) fragmentView.findViewById(R.id.section_label);

        ImageView lampOne = (ImageView) fragmentView.findViewById(R.id.dragImageOne);
        ImageView lampTwo = (ImageView) fragmentView.findViewById(R.id.dragImageTwo);
        ImageView lampThree = (ImageView) fragmentView.findViewById(R.id.dragImageThree);
        ImageView room = (ImageView) fragmentView.findViewById(R.id.dropImage);

        lampOne.setOnLongClickListener(longClickListener);
        lampTwo.setOnLongClickListener(longClickListener);
        lampThree.setOnLongClickListener(longClickListener);

        lampOne.setOnClickListener(clickListener);
        lampTwo.setOnClickListener(clickListener);
        lampThree.setOnClickListener(clickListener);

        lampOne.setTag(UUID.randomUUID());
        lampTwo.setTag(UUID.randomUUID());
        lampThree.setTag(UUID.randomUUID());

        unitList.add(lampOne);
        unitList.add(lampTwo);
        unitList.add(lampThree);

        //addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 300, 200));

//        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 60, 30));
//        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 200, 130));
//        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 70, 90));

        room.setOnDragListener(dropListener);

        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

        setHasOptionsMenu(true);

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.room_config_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                addNewUnitToRoom();
                addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 150, 150));
                return true;
            case R.id.action_selection:
                switchAllSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewUnitToRoom() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getActivity().getBaseContext().getResources() /*Resources.getSystem()*/, R.drawable.ic_lightbulb);

        ImageView iv = new ImageView(this.getActivity().getBaseContext());
        iv.setOnLongClickListener(longClickListener);
        iv.setOnClickListener(clickListener);
        iv.setImageBitmap(bitmap);
        iv.setTop(350);
        iv.setLeft(350);
        iv.setTag(UUID.randomUUID());

        unitList.add(iv);

        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.layout_room_setup);
        layout.addView(iv);
    }

    private void addNewUnitToRoom(GraphicUnit gUnit) {
        unitHash.put(gUnit.id, gUnit);
        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.layout_room_setup);
        layout.addView(gUnit.getView(fragmentView.getContext()));
    }

    public View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData clipData = ClipData.newPlainText("category", "value");
            v.startDrag(clipData, new OldDragShadow (v), v, 0);
            return false;
        }
    };

    public class OldDragShadow extends View.DragShadowBuilder {

        int shadowDiameter = 200;

        public OldDragShadow (View v) {
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
    };

    View.OnDragListener dropListener = new View.OnDragListener() {
        float dragXDiff = 0;
        float dragYDiff = 0;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("DragEvent", "Entered");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("DragEvent", "Ended");
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    ImageView draggedView = (ImageView) event.getLocalState();
                    Log.i("DragEvent", "Started at LAMP = " + draggedView.getX() + "/" + draggedView.getY() + "   EVENT = " + event.getX() + "/" + event.getY());
                    dragXDiff = event.getX() - draggedView.getX();
                    dragYDiff = event.getY() - draggedView.getY();
                    //stop displaying the view where it was before it was dragged
                    draggedView.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    ImageView droppedView = (ImageView) event.getLocalState();
                    Log.i("DragEvent", "Dropped at LAMP = " + Math.round(event.getX() + dragXDiff) + "/" + Math.round(event.getY() + dragYDiff) + "   EVENT = " + event.getX() + "/" + event.getY());
                    Log.i("DragEvent", "Drop target at TOP = " + v.getTop() + "   LEFT = " + v.getLeft());
                    droppedView.setX(Math.round(event.getX() + dragXDiff + v.getLeft() - 70));
                    droppedView.setY(Math.round(event.getY() + dragYDiff/* + v.getTop()*/ - 50));
                    droppedView.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    };

    private void switchAllSelection() {
        Iterator iterator = unitList.iterator();
        while(iterator.hasNext()) {
            ImageView iv = (ImageView) iterator.next();
            setSelected(iv, doSelectAll);
        }
        iterator = unitHash.values().iterator();
        while(iterator.hasNext()) {
            GraphicUnit gu = (GraphicUnit) iterator.next();
            setSelected(gu, doSelectAll);
        }

        doSelectAll = !doSelectAll;
    }

    private void setSelected(GraphicUnit gu, boolean selected) {
        if(gu != null && gu.isSelected != selected)
            gu.getView(this.getActivity().getApplicationContext()/* fragmentView.getContext()*/).callOnClick();//TODO - Remove call to onClick.
    }

    private void setSelected(ImageView iv, boolean selected) {
        if(iv != null && iv.isSelected() != selected)
            iv.callOnClick();//TODO - Remove call to onClick.
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        Bitmap originalBitmap;

        @Override
        public void onClick(View v) {
            Log.i("Click", "View status BEFORE = " + (v.isSelected()? "Selected" : "Not selected"));

            if(!v.isSelected()) {
                v.setSelected(true);
                Drawable drawable = ((ImageView) v).getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                originalBitmap = ((BitmapDrawable) drawable).getBitmap().copy(Bitmap.Config.ARGB_8888, true);

                Rect bounds = drawable.getBounds();
                int width = bounds.width();
                int height = bounds.height();
                int bitmapWidth = drawable.getIntrinsicWidth();
                int bitmapHeight = drawable.getIntrinsicHeight();
                Log.i("Bitmap", "Height = " + bitmapHeight + "   Width = " + bitmapWidth);

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);

                Canvas canvas = new Canvas(bitmap);

                canvas.drawCircle(canvas.getHeight()/2, canvas.getWidth()/2, (float) Math.floor(bitmapHeight/2) - Math.round(paint.getStrokeWidth()/2), paint);
                ((ImageView) v).setImageBitmap(bitmap);
            } else {
                v.setSelected(false);
                ((ImageView) v).setImageBitmap(originalBitmap);
            }
            Log.i("Click", "View status AFTER = " + (v.isSelected()? "Selected" : "Not selected"));
        }
    };
}

