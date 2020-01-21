package com.punzar.funboxtest;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPhonesListInteractionListener}
 * interface.
 */
public class BackEndFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PHONES = "PHONES";
    private OnPhonesListInteractionListener mListener;
    private List<SmartPhone> mPhones;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BackEndFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BackEndFragment newInstance(List<SmartPhone> phones) {
        BackEndFragment fragment = new BackEndFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PHONES, (ArrayList<? extends Parcelable>) phones);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPhones = getArguments().getParcelableArrayList(ARG_PHONES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back_end, container, false);
        Button btnAdd = view.findViewById(R.id.btn_add);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SmartPhoneRecyclerViewAdapter(mPhones, mListener));
        btnAdd.setOnClickListener(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPhonesListInteractionListener) {
            mListener = (OnPhonesListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPhonesListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        mListener.onListFragmentInteraction(null, mPhones.size());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPhonesListInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(SmartPhone item, int position);
    }
}
