package com.zenit.dragndrop;

/**
 * Created by Tony Alpskog on 2013-12-22.
 */

import android.app.Fragment;
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

        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) fragmentView.findViewById(R.id.section_label);
        ImageView room = (ImageView) fragmentView.findViewById(R.id.dropImage);

        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 60, 30));
        addNewUnitToRoom(new GraphicUnit(UnitType.VENT, 200, 130));
        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 70, 90));

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
                addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH, 150, 150));
                return true;
            case R.id.action_selection:
                switchAllSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewUnitToRoom(GraphicUnit gUnit) {
        unitHash.put(gUnit.id, gUnit);
        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.layout_room_setup);
        layout.addView(gUnit.getView(fragmentView.getContext()));
    }

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
        Iterator iterator = unitHash.values().iterator();
        while(iterator.hasNext()) {
            GraphicUnit gu = (GraphicUnit) iterator.next();
            setSelected(gu, doSelectAll);
        }

        doSelectAll = !doSelectAll;
    }

    private void setSelected(GraphicUnit gu, boolean selected) {
        if(gu != null && gu.isSelected() != selected)
            gu.setSelected(selected);
    }
}

