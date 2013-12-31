package com.zenit.habclient;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.zenit.navndrawer.R;

import java.util.ArrayList;
import java.util.Iterator;

public class RoomConfigFragment extends Fragment {

    private RoomProvider mRoomProvider;
    private Room mCurrentRoom;
    private boolean isNewRoom = false;

//    private OnFragmentInteractionListener mListener;

    public static RoomConfigFragment newInstance(RoomProvider roomProvider, Room roomToBeEdited) {
        RoomConfigFragment fragment = new RoomConfigFragment(roomProvider, roomToBeEdited);
        return fragment;
    }

    public RoomConfigFragment(RoomProvider roomProvider, Room roomToBeEdited) {
        mRoomProvider = roomProvider;
        mCurrentRoom = roomToBeEdited;
        isNewRoom = (roomToBeEdited == null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("LifeCycle", "RoomConfigFragment.onCreateView()");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_room, container, false);
        Spinner leftRoomSpinner = (Spinner) view.findViewById(R.id.spinner_left_room);
        Spinner rightRoomSpinner = (Spinner) view.findViewById(R.id.spinner_right_room);
        Spinner upRoomSpinner = (Spinner) view.findViewById(R.id.spinner_up_room);
        Spinner downRoomSpinner = (Spinner) view.findViewById(R.id.spinner_down_room);
        Spinner aboveRoomSpinner = (Spinner) view.findViewById(R.id.spinner_above_room);
        Spinner belowRoomSpinner = (Spinner) view.findViewById(R.id.spinner_below_room);

        ArrayList<Room> roomArrayList = new ArrayList<Room>(mRoomProvider.roomHash.size());
        Iterator iterator = mRoomProvider.roomHash.values().iterator();
        while(iterator.hasNext()) {
            roomArrayList.add((Room) iterator.next());
        }
        ArrayAdapter<Room> roomSpinnerAdapter = new ArrayAdapter<Room>(this.getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, roomArrayList);
        roomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        leftRoomSpinner.setAdapter(roomSpinnerAdapter);
        rightRoomSpinner.setAdapter(roomSpinnerAdapter);
        upRoomSpinner.setAdapter(roomSpinnerAdapter);
        downRoomSpinner.setAdapter(roomSpinnerAdapter);
        aboveRoomSpinner.setAdapter(roomSpinnerAdapter);
        belowRoomSpinner.setAdapter(roomSpinnerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LifeCycle", "RoomConfigFragment.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycle", "RoomConfigFragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LifeCycle", "RoomConfigFragment.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LifeCycle", "RoomConfigFragment.onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("LifeCycle", "RoomConfigFragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "RoomConfigFragment.onDestroy()");
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("LifeCycle", "RoomConfigFragment.onAttach()");
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("LifeCycle", "RoomConfigFragment.onDetach()");
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

    

}
