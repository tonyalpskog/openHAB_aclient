package com.zenit.dragndrop;

/**
 * Created by Tony Alpskog in 2013.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
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

import com.zenit.navndrawer.HABApplication;
import com.zenit.navndrawer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    private HashMap<UUID, GraphicUnit> unitHash;
    private View fragmentView;
    private ImageView roomView;
    private float lastRoomX, lastRoomY;
    private int lastRoomWidth, lastRoomHeight;

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
        Log.d("LifeCycle", "RoomConfigFragment(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ") <constructor>");
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Log.d("LifeCycle", "RoomConfigFragment.onInflate(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("LifeCycle", "RoomConfigFragment.onInflate(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LifeCycle", "RoomConfigFragment.onCreateView(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
        unitHash = ((HABApplication) getActivity().getApplication()).getUnitHash(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

        if(unitHash == null)
            unitHash = new HashMap<UUID, GraphicUnit>();

        fragmentView = inflater.inflate(R.layout.fragment_room_config, container, false);
        TextView textView = (TextView) fragmentView.findViewById(R.id.room_config_section_label);
        roomView = (ImageView) fragmentView.findViewById(R.id.dropImage);

        roomView.setOnDragListener(dropListener);

        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

        setHasOptionsMenu(true);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LifeCycle", "RoomConfigFragment.onStart(" + (getArguments() != null ? getArguments().getInt(ARG_SECTION_NUMBER) : "?") + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycle", "RoomConfigFragment.onResume(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
        Iterator unitIterator = unitHash.values().iterator();
        GraphicUnit graphicUnit;
        while (unitIterator.hasNext()) {
            graphicUnit = (GraphicUnit) unitIterator.next();
            Log.d("UnitPos", "onResume REL: " + graphicUnit.roomRelativeX + "/" + graphicUnit.roomRelativeY + "   Room: " + roomView.getX() + "/" + roomView.getY());
            graphicUnit.resetView();
            Log.d("UnitPos", "onResume REL: " + graphicUnit.roomRelativeX + "/" + graphicUnit.roomRelativeY + "   Room: " + roomView.getX() + "/" + roomView.getY());
            drawUnitInRoom(graphicUnit);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LifeCycle", "RoomConfigFragment.onPause(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LifeCycle", "RoomConfigFragment.onStop(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LifeCycle", "RoomConfigFragment.onDestroyView(" + (getArguments() != null ? getArguments().getInt(ARG_SECTION_NUMBER) : "?") + ")");
        //TODO - Add this as Bundle
        lastRoomX = roomView.getX();
        lastRoomY = roomView.getY();
        lastRoomWidth = roomView.getWidth();
        lastRoomHeight = roomView.getHeight();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "RoomConfigFragment.onDestroy(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LifeCycle", "RoomConfigFragment.onDetach(" + (getArguments()!=null? getArguments().getInt(ARG_SECTION_NUMBER): "?") + ")");
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
                showAddUnitDialog(getActivity());
                return true;
            case R.id.action_selection:
                unitSelectionDialog(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddUnitDialog(Context context) {
        final CharSequence[] items = {"Switch", "Dimmer", "Heating", "Vent", "Socket"};

        AlertDialog addUnitDialog;
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setOnDismissListener(new MyOnDismissListener());
        builder.setTitle("Select unit type");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        addNewUnitToRoom(new GraphicUnit(UnitType.SWITCH), 150, 150);
                        break;
                    case 1:
                        addNewUnitToRoom(new GraphicUnit(UnitType.DIMMER), 150, 150);
                        break;
                    case 2:
                        addNewUnitToRoom(new GraphicUnit(UnitType.ROOM_HEATER), 150, 150);
                        break;
                    case 3:
                        addNewUnitToRoom(new GraphicUnit(UnitType.VENT), 150, 150);
                        break;
                    case 4:
                        addNewUnitToRoom(new GraphicUnit(UnitType.SOCKET), 150, 150);
                        break;

                }
                dialog.dismiss();
            }
        });
        addUnitDialog = builder.create();
        addUnitDialog.show();

        class MyOnDismissListener implements DialogInterface.OnDismissListener {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        }
    }

    private void unitSelectionDialog(Context context) {
        final CharSequence[] items = {"Select all", "Deselect all", "Select all of current type(s)"};

        AlertDialog selectUnitDialog;
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose selection type");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        multiUnitSelection(true);
                        break;
                    case 1:
                        multiUnitSelection(false);
                        break;
                    case 2:
                        ArrayList<UnitType> selectedTypes = new ArrayList<UnitType>();

                        Iterator iterator = unitHash.values().iterator();
                        while(iterator.hasNext()) {
                            GraphicUnit gu = (GraphicUnit) iterator.next();
                            if(gu.isSelected() && !selectedTypes.contains(gu.type))
                                selectedTypes.add(gu.type);
                        }

                        iterator = unitHash.values().iterator();
                        while(iterator.hasNext()) {
                            GraphicUnit gu = (GraphicUnit) iterator.next();
                            if(!gu.isSelected() && selectedTypes.contains(gu.type))
                                gu.setSelected(true);
                        }
                        break;
                }
                dialog.dismiss();
            }
        });
        selectUnitDialog = builder.create();
        selectUnitDialog.show();
    }

    private void addNewUnitToRoom(GraphicUnit gUnit, int x, int y) {
        unitHash.put(gUnit.id, gUnit);
        drawUnitInRoom(gUnit, x, y);
    }

    private void drawUnitInRoom(GraphicUnit gUnit) {
        int x = roomView.getX() != 0? Math.round(roomView.getX() + (roomView.getWidth() / gUnit.roomRelativeX)): Math.round(lastRoomX + (lastRoomWidth / gUnit.roomRelativeX));
        int y = roomView.getY() != 0? Math.round(roomView.getY() + (roomView.getHeight() / gUnit.roomRelativeY)): Math.round(lastRoomY + (lastRoomHeight / gUnit.roomRelativeY));
        drawUnitInRoom(gUnit, x, y);
    }

    private void drawUnitInRoom(GraphicUnit gUnit, int x, int y) {
        RelativeLayout layout = (RelativeLayout) fragmentView.findViewById(R.id.layout_room_setup);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(x, y, 0, 0);
        Log.d("UnitPos", params.leftMargin + "/" + params.topMargin);
        View gView = gUnit.getView(fragmentView.getContext());
        layout.addView(gView, params);
        Log.d("UnitPos", "draw REL: " + gUnit.roomRelativeX + "/" + gUnit.roomRelativeY + "   Calc: X=(" + roomView.getWidth() + "/(" + gView.getX() + "-" + roomView.getX() + ")  Y=(" + roomView.getHeight() + "/(" + gView.getY() + "-" + roomView.getY() + ")");
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

                    GraphicUnit gUnit = unitHash.get(droppedView.getTag());
                    gUnit.roomRelativeX = roomView.getWidth() / (droppedView.getX() - roomView.getX());
                    gUnit.roomRelativeY = roomView.getHeight() / (droppedView.getY() - roomView.getY());

                    Log.d("UnitPos", droppedView.getX() + "/" + droppedView.getY());
                    Log.d("UnitPos", "dropped REL: " + gUnit.roomRelativeX + "/" + gUnit.roomRelativeY + "   Calc: X=(" + roomView.getWidth() + "/(" + droppedView.getX() + "-" + roomView.getX() + ")  Y=(" + roomView.getHeight() + "/(" + droppedView.getY() + "-" + roomView.getY() + ")");
                    break;
            }
            return true;
        }
    };

    private void multiUnitSelection(boolean doSelect) {
        Iterator iterator = unitHash.values().iterator();
        while(iterator.hasNext()) {
            GraphicUnit gu = (GraphicUnit) iterator.next();
            setSelected(gu, doSelect);
        }
    }

    private void setSelected(GraphicUnit gu, boolean selected) {
        if(gu != null && gu.isSelected() != selected)
            gu.setSelected(selected);
    }
}

