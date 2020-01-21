package com.punzar.funboxtest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PhonesDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhonesDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PHONE = "PHONE";

    private String mName;
    private double mPrice;
    private int mCount;
    private SmartPhone mPhone;
    private TextView mTvCount;

//    private OnEditBtnClcListener mListener;

    public PhonesDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phone Count of SmartPhone
     * @return A new instance of fragment PhoneDetailsFragment.
     */
    public static PhonesDetailsFragment newInstance(SmartPhone phone) {
        PhonesDetailsFragment fragment = new PhonesDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhone = getArguments().getParcelable(ARG_PHONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phones_details, container, false);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        mTvCount = view.findViewById(R.id.tv_count);
        Button btnBuy = view.findViewById(R.id.btn_buy);

        tvName.setText(mPhone.getName());
        String price = mPhone.getPrice() + getResources().getString(R.string.currency);
        tvPrice.setText(price);
        String count = mPhone.getCount() + getResources().getString(R.string.pieces);
        mTvCount.setText(count);
        btnBuy.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(int id) {
//        if (mListener != null) {
//            mListener.onEditBtnClicked(id);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnEditBtnClcListener) {
//            mListener = (OnEditBtnClcListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnEditBtnClcListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_buy){

            if(mPhone.getCount() == 1){
                mPhone.setCount(mPhone.getCount()-1);
                //todo листнуть и больше не показывать
                
            }
            mPhone.setCount(mPhone.getCount()-1);
            String count = mPhone.getCount() + getResources().getString(R.string.pieces);
            mTvCount.setText(count);
        }
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
//    public interface OnEditBtnClcListener {
//        // TODO: Update argument type and name
//        void onEditBtnClicked(int id);
//    }
}
