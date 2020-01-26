package com.punzar.funboxtest;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punzar.funboxtest.BackEndFragment.OnPhonesListInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class SmartPhoneRecyclerViewAdapter extends RecyclerView.Adapter<SmartPhoneRecyclerViewAdapter.ViewHolder> {

    private final List<SmartPhone> mPhones;
    private final OnPhonesListInteractionListener mListener;

    public SmartPhoneRecyclerViewAdapter(List<SmartPhone> phones, OnPhonesListInteractionListener listener) {
        mPhones = phones;
        mListener = listener;
    }

    public void insertData(SmartPhone phone) {
        List<SmartPhone> newList = new ArrayList<>();
        newList.add(phone);
        BackEndDiffUtilCallback diffUtillCallback = new BackEndDiffUtilCallback(mPhones, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtillCallback);

        mPhones.addAll(newList);
        diffResult.dispatchUpdatesTo(this);


    }

    public void updateData(List<SmartPhone> newList) {
        BackEndDiffUtilCallback diffUtillCallback = new BackEndDiffUtilCallback(mPhones, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtillCallback);

        mPhones.clear();
        mPhones.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
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
