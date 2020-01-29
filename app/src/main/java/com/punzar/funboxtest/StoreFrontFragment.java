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
    private static final String KEY_VIEW_PAGER = "KEY_VIEW_PAGER";

    private ViewPager pager;
    private StorePagerAdapter pagerAdapter;
    private List<SmartPhone> mPhonesSuperList, mBalancePhones;
    private Handler handler;
    private int mPagerPosition = 0;

    public StoreFrontFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phones List of SmartPhone for ViewPager.
     * @return A new instance of fragment StoreFrontFragment.
     */
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
        if(savedInstanceState != null) {
            mPagerPosition = savedInstanceState.getInt(KEY_VIEW_PAGER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_front, container, false);

//        if (savedInstanceState != null) {
//            mBalancePhones = savedInstanceState.getParcelableArrayList(KEY_BALANCE_PHONE);
//        } else {
            collectPhones();
//        }

        pager = view.findViewById(R.id.vp_store_front);
        pagerAdapter = new StorePagerAdapter(getChildFragmentManager());
        pagerAdapter.setPages(mBalancePhones);
        pager.setAdapter(pagerAdapter);
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                int count = msg.what;
                int position = msg.arg1;
                int size = msg.arg2;
                if (count == 0) {

                    pagerAdapter.notifyDataSetChanged();
                    if (position == size - 1){
                        pager.setCurrentItem(position - 1, true);
                        mBalancePhones.remove(position);
                        pagerAdapter.notifyDataSetChanged();
                    }else {
                        pager.setCurrentItem(position, true);
                        mBalancePhones.remove(position);
                        pagerAdapter.notifyDataSetChanged();
                    }
                } else {
                    pagerAdapter.notifyDataSetChanged();
                }
                try {
                    MainActivity.ReadWriteAdapter readWriteAdapter = new SaveLoadService();
                    readWriteAdapter.write(getContext(), mPhonesSuperList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onStop() {
        super.onStop();
//        Bundle bundle = new Bundle();
//        bundle.putInt(KEY_VIEW_PAGER, pager.getCurrentItem());
//        onSaveInstanceState(bundle);
    }

    @Override
    public void onBuyBtnClicked(final int position, final SmartPhone phone) {
        Thread thread = new Thread(new Runnable() {
            Message msg;

            @Override
            public void run() {

                int count = phone.getCount();
                int size = mBalancePhones.size();
//                if (count == 0) {
//
//                    if (position < mBalancePhones.size()) {
////                        pager.setCurrentItem(position - 1, true);
//                        mBalancePhones.remove(position);
////                        pagerAdapter.notifyDataSetChanged();
//                    }
//
//                } else {
//                    mBalancePhones.set(position, phone);
//                }
                mBalancePhones.set(position, phone);
                msg = handler.obtainMessage(count, position, size, null);
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_BALANCE_PHONE, (ArrayList<? extends Parcelable>) mBalancePhones);
        outState.putInt(KEY_VIEW_PAGER, pager.getCurrentItem());
    }

    private class StorePagerAdapter extends FragmentStatePagerAdapter {

        private List<SmartPhone> pages = new ArrayList<>();

        public void setPages(List<SmartPhone> pages) {
            this.pages = pages;
            notifyDataSetChanged();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
        }

        @Override
        public Parcelable saveState() {
            return super.saveState();
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
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
