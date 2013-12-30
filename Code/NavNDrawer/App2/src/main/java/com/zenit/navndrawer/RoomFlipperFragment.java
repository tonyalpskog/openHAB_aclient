package com.zenit.navndrawer;

/**
 * Created by Tony Alpskog in 2013.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoomFlipperFragment extends Fragment implements RoomFlipper.OnRoomShiftListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RoomFlipper roomViewFlipper;
    TextView textView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RoomFlipperFragment newInstance(int sectionNumber) {
        RoomFlipperFragment fragment = new RoomFlipperFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomFlipperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_room_flipper, container, false);
        textView = (TextView) rootView.findViewById(R.id.room_flipper_section_label);
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

        roomViewFlipper = (RoomFlipper) rootView.findViewById(R.id.flipper);

        roomViewFlipper.setDisplayedChild(0);//Show middle image as initial image
        roomViewFlipper.setGestureListener(new GestureListener(rootView));
        roomViewFlipper.setOnRoomShiftListener(this);
        roomViewFlipper.setRoomFlipperAdapter(new RoomFlipperAdapter(rootView.getContext(), ((HABApplication) getActivity().getApplication()).getFlipperRoom()), (HABApplication) getActivity().getApplication());

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO - Create dynamic menu
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.room_flipper_default, menu);

        //menu.findItem(R.id.action_remove).setVisible()
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_edit_room_from_flipper:
                Room roomToEdit = ((HABApplication) getActivity().getApplication()).getFlipperRoom();
                Log.d("Edit Room", "onOptionsItemSelected() - Edit room action on room<" + roomToEdit.getId() + ">");
                ((HABApplication) getActivity().getApplication()).setConfigRoom(roomToEdit);
                ((MainActivity) getActivity()).selectNavigationDrawerItem(1);//TODO - Use enum as fragment identifier.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onRoomShift(Gesture gesture, Room room) {
        Log.d("Flip Room", "onRoomShift() - Shifted to room<" + room.getId() + ">");
        textView.setText("Last direction: " + gesture.name());
        ((HABApplication) getActivity().getApplication()).setFlipperRoom(room);
        return false;
    }
}

