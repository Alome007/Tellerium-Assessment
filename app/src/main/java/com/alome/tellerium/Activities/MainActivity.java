package com.alome.tellerium.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.alome.tellerium.Fragments.addFarmers;
import com.alome.tellerium.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.alome.tellerium.Fragments.Dashboard;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener{
    ChipNavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        navigationBar.setItemSelected(R.id.home, true);
        loadFragment(new Dashboard());
    }

    private void initUI() {
        navigationBar=findViewById(R.id.bottom_menu);
    }

    @Override
    public void onItemSelected(int i) {
        Fragment fragment=null;
        switch (i){
            case R.id.home:
                loadFragment(new Dashboard());
                break;


        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
