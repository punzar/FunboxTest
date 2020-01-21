package com.punzar.funboxtest;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punzar.funboxtest.BackEndFragment.OnPhonesListInteractionListener;

import java.util.List;

public class SmartPhoneRecyclerViewAdapter extends RecyclerView.Adapter<SmartPhoneRecyclerViewAdapter.ViewHolder> {

    private final List<SmartPhone> mPhones;
    private final OnPhonesListInteractionListener mListener;

    public SmartPhoneRecyclerViewAdapter(List<SmartPhone> phones, OnPhonesListInteractionListener listener) {
        mPhones = phones;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_smartphone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mPhoneItem = mPhones.get(position);
        holder.mName.setText(mPhones.get(position).getName());
        holder.mCount.setText(String.valueOf(mPhones.get(position).getCount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mPhoneItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public SmartPhone mPhoneItem;

        public final TextView mName;
        public final TextView mCount;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.tv_item_name);
            mCount = view.findViewById(R.id.tv_item_count);
        }


    }
}
