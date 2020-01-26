package com.punzar.funboxtest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StoreFrontFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFrontFragment extends Fragment implements PhonesDetailsFragment.OnBuyBtnClcListener {
    private static final String ARG_LIST = "LIST";
    private static final String KEY_BALANCE_PHONE = "KEY_BALANCE_PHONE";

    private ViewPager pager;
    private StorePagerAdapter pagerAdapter;
    private List<SmartPhone> mPhonesSuperList, mBalancePhones;
    private Handler handler;

    public StoreFrontFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phones List of SmartPhone for ViewPager.
     * @return A new instance of fragment StoreFrontFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFrontFragment newInstance(List<SmartPhone> phones) {
        StoreFrontFragment fragment = new StoreFrontFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, (ArrayList<? extends Parcelable>) phones);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhonesSuperList = getArguments().getParcelableArrayList(ARG_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_front, container, false);

        if (savedInstanceState != null) {
            mBalancePhones = savedInstanceState.getParcelableArrayList(KEY_BALANCE_PHONE);
        } else {
            collectPhones();
        }

        pager = view.findViewById(R.id.vp_store_front);
        pagerAdapter = new StorePagerAdapter(getChildFragmentManager());
        pagerAdapter.setPages(mBalancePhones);
        pager.setAdapter(pagerAdapter);
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                int count = msg.what;
                int position = msg.arg1;
                if (count == 0) {
                    collectPhones();
                    pagerAdapter.notifyDataSetChanged();
                    pager.setCurrentItem(position, true);
                } else {
                    pagerAdapter.notifyDataSetChanged();
                }
                try {
                    SaveLoadAdapter.save(getContext(), mPhonesSuperList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        return view;
    }

    private void collectPhones() {
        if (mPhonesSuperList != null) {
            mBalancePhones = new ArrayList<>();
            for (SmartPhone item : mPhonesSuperList) {
                if (item.getCount() > 0) {
                    mBalancePhones.add(item);
                }
            }
        } else {
            Toast.makeText(getContext(), "Some thig wrong with Phone List", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onBuyBtnClicked(final int position, final SmartPhone phone) {
        Thread thread = new Thread(new Runnable() {
            Message msg;

            @Override
            public void run() {

                int count = phone.getCount();
                if (count == 0) {

                    if (position < mBalancePhones.size())
                        mBalancePhones.remove(position);

                } else {
                    mBalancePhones.set(position, phone);
                }
                msg = handler.obtainMessage(count, position, 0, null);
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_BALANCE_PHONE, (ArrayList<? extends Parcelable>) mBalancePhones);
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

    private class StorePagerAdapter extends FragmentStatePagerAdapter {

        private List<SmartPhone> pages = new ArrayList<>();

        public void setPages(List<SmartPhone> pages) {
            this.pages = pages;
            notifyDataSetChanged();
        }


        public StorePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return PhonesDetailsFragment.newInstance(pages.get(position), position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
