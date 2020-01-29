package com.punzar.funboxtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        BackEndFragment.OnPhonesListInteractionListener, EditSmartPhone.OnEditBtnClcListener,
        StoreFrontFragment.OnSavePagerPosition {

    private static final String TAG_STORE_FRONT = "STORE_FRONT";
    private static final String TAG_BACK_END = "BACK_END";
    private static final String TAG_EDIT_SMART_PHONE = "EDIT_SMART_PHONE";
    private static final String KEY_LIST = "KEY_LIST";
    private static final String KEY_POSITION = "KEY_POSITION";
    private static Handler handler;

    private List<SmartPhone> mList;
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_bar);
        ReadWriteAdapter readWriteAdapter = new SaveLoadService();
        if (savedInstanceState != null) {
            mList = savedInstanceState.getParcelableArrayList(KEY_LIST);
            mPosition = savedInstanceState.getInt(KEY_POSITION);
        } else {

            try {
                mList = readWriteAdapter.read(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bottomNavView.setOnNavigationItemSelectedListener(this);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (currentFragment == null) {
            bottomNavView.setSelectedItemId(R.id.bottom_store_front);
        }

        handler = new Handler();
        try {
            readWriteAdapter.write(this, mList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottom_store_front:
                setFragment(StoreFrontFragment.newInstance(mList, mPosition), TAG_STORE_FRONT);
                break;
            case R.id.bottom_back_end:
                setFragment(BackEndFragment.newInstance(mList), TAG_BACK_END);
                break;
        }
        return true;
    }

    private void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frame_layout, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(SmartPhone item, int position) {
        setFragment(EditSmartPhone.newInstance(item, position), TAG_EDIT_SMART_PHONE);
    }

    @Override
    public void onEditBtnClicked(View view, final SmartPhone phone, final int position) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (position < mList.size()) {
                    final Context context = this;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<SmartPhone> newList = new ArrayList<>(mList);
                            newList.set(position, phone);
                            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
                            if (fragment instanceof EditSmartPhone) {
                                ((EditSmartPhone) fragment).turnOnButton();
                            }
                            if (fragment instanceof BackEndFragment) {
                                ((BackEndFragment) fragment).updateRV(newList);
                            }
                            mList = newList;
                            try {
                                ReadWriteAdapter readWriteAdapter = new SaveLoadService();
                                readWriteAdapter.write(context, mList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context, "Data update", Toast.LENGTH_SHORT).show();
                        }
                    }, 5 * 1000);

                    break;
                } else {
                    final Context context = this;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);

                            if (fragment instanceof EditSmartPhone) {
                                ((EditSmartPhone) fragment).turnOnButton();
                            }
                            if (fragment instanceof BackEndFragment) {
                                ((BackEndFragment) fragment).insertToRV(phone);
                            } else {
                                mList.add(phone);
                            }
                            Toast.makeText(context, "New device add", Toast.LENGTH_SHORT).show();
                            try {
                                ReadWriteAdapter readWriteAdapter = new SaveLoadService();
                                readWriteAdapter.write(context, mList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 5 * 1000);
                    break;
                }
            case R.id.btn_cancel:
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST, (ArrayList<? extends Parcelable>) mList);
        outState.putInt(KEY_POSITION, mPosition);
    }

    @Override
    public void onSavePagerPosition(int position) {
        mPosition = position;
    }

    public interface ReadWriteAdapter {
        void write(Context context, List<SmartPhone> smartPhoneList) throws IOException;

        List<SmartPhone> read(Context context) throws IOException;
    }
}
