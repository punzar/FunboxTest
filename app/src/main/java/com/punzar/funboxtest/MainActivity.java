package com.punzar.funboxtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        BackEndFragment.OnPhonesListInteractionListener, EditSmartPhone.OnEditBtnClcListener {

    private BottomNavigationView mBottomNavView;

    List<SmartPhone> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavView = findViewById(R.id.bottom_nav_bar);

        try {
            list = new CsvReader().readCsv(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBottomNavView.setOnNavigationItemSelectedListener(this);
        mBottomNavView.setSelectedItemId(R.id.bottom_store_front);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottom_store_front:
                setFragment(StoreFrontFragment.newInstance(list));
                break;
            case R.id.bottom_back_end:
                setFragment(BackEndFragment.newInstance(list));
                break;
        }
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(SmartPhone item, int position) {
        //Toast.makeText(this,"YES " + item.getName(),Toast.LENGTH_SHORT).show();
        setFragment(EditSmartPhone.newInstance(item, position));
        //list.set(position, item);

    }

    @Override
    public void onEditBtnClicked(View view, SmartPhone phone, int position) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (position < list.size()) {
                    list.set(position, phone);
                    setFragment(BackEndFragment.newInstance(list));
                    break;
                } else {
                    list.add(phone);
                    setFragment(BackEndFragment.newInstance(list));
                    break;
                }
            case R.id.btn_cancel:
                setFragment(BackEndFragment.newInstance(list));
                break;
        }

//        Toast.makeText(this, "PRESSED " + phone.getName(), Toast.LENGTH_SHORT).show();
    }
}
