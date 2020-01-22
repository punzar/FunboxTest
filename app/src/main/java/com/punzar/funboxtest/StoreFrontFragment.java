package com.punzar.funboxtest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST = "LIST";

    // TODO: Rename and change types of parameters

    private ViewPager pager;
    private StorePagerAdapter pagerAdapter;
    private List<SmartPhone> mPhonesSuperList, mBalancePhones;

//    private OnEditBtnClcListener mListener;

    public StoreFrontFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_front, container, false);

        collectPhones();
        pager = view.findViewById(R.id.vp_store_front);
        pagerAdapter = new StorePagerAdapter(getChildFragmentManager());
        pagerAdapter.setPages(mBalancePhones);
        pager.setAdapter(pagerAdapter);

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

    private void backSort(){
        for (SmartPhone superPhone : mPhonesSuperList){
            boolean hasChanged = false;
            for(SmartPhone sortedPhone : mBalancePhones) {
               if(superPhone.getName().equals(sortedPhone.getName())){
                   superPhone.setCount(sortedPhone.getCount());
                   hasChanged = true;
                   break;
               }
            }
            if(!hasChanged){
                superPhone.setCount(0);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onEditBtnClicked(uri);
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
    public void onBuyBtnClicked(int position, SmartPhone phone) {
        if(phone.getCount() == 0){
            mBalancePhones.remove(position);
            pagerAdapter.notifyDataSetChanged();
            pager.setCurrentItem(position, true);

        }else {
            mBalancePhones.set(position, phone);
        }
        pagerAdapter.notifyDataSetChanged();

//        pager.setCurrentItem();
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

//    @Override
//    public void onNewsItemCLick(NewsEvent event) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("NewsEvent", event);
//        Fragment detailsFragment = new DetailsFragment();
//        detailsFragment.setArguments(bundle);
//
//        setFragment(detailsFragment, true);
//
//    }

//    public interface OnEditBtnClcListener {
//        // TODO: Update argument type and name
//        void onEditBtnClicked(Uri uri);
//    }

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
//            }
        }



    }
}
