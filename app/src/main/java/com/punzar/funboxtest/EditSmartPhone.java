package com.punzar.funboxtest;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEditBtnClcListener} interface
 * to handle interaction events.
 * Use the {@link EditSmartPhone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSmartPhone extends Fragment implements View.OnClickListener {
    private static final String ARG_PHONE = "ARG_PHONE";
    private static final String ARG_POSITION = "POSITION";
    // TODO: Rename and change types of parameters
    private SmartPhone mPhone;
    private int mPosition;
    private EditText mNameET, mPriceET, mCountET;

    private OnEditBtnClcListener mListener;

    public EditSmartPhone() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phone    may be null
     * @param position
     * @return A new instance of fragment EditSmartPhone.
     */
    // TODO: Rename and change types and number of parameters
    public static EditSmartPhone newInstance(SmartPhone phone, int position) {
        EditSmartPhone fragment = new EditSmartPhone();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHONE, phone);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhone = getArguments().getParcelable(ARG_PHONE);
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_smart_phone, container, false);
        mNameET = view.findViewById(R.id.et_name);
        mPriceET = view.findViewById(R.id.et_price);
        mCountET = view.findViewById(R.id.et_count);
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (mPhone != null) {
            mNameET.setText(mPhone.getName());
            mPriceET.setText(String.valueOf(mPhone.getPrice()));
            mCountET.setText(String.valueOf(mPhone.getCount()));
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onEditBtnClicked(view, mPhone, mPosition);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditBtnClcListener) {
            mListener = (OnEditBtnClcListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditBtnClcListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (mNameET.getText().toString().matches("") ||
                        mPriceET.getText().toString().matches("")
                        || mCountET.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Необходимо заполнить все поля.",
                            Toast.LENGTH_LONG).show();
                    break;
                }
                if (mPhone != null) {
                    mPhone.setName(mNameET.getText().toString());
                    mPhone.setPrice(Double.parseDouble(mPriceET.getText().toString()));
                    mPhone.setCount(Integer.parseInt(mCountET.getText().toString()));
                    onButtonPressed(view);
                    break;
                } else {
                    mPhone = new SmartPhone(mNameET.getText().toString(),
                            Double.parseDouble(mPriceET.getText().toString()),
                            Integer.parseInt(mCountET.getText().toString()));
                    onButtonPressed(view);
                    break;
                }

            case R.id.btn_cancel:
                onButtonPressed(view);
                break;
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
    public interface OnEditBtnClcListener {
        // TODO: Update argument type and name
        void onEditBtnClicked(View view, SmartPhone phone, int position);
    }
}

