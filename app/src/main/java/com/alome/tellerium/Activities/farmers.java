package com.alome.tellerium.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Adapters.farmersAdapter;
import com.alome.tellerium.Fragments.viewFarmer;
import com.alome.tellerium.Models.farmerModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;
import com.google.gson.Gson;

import java.util.ArrayList;

public class farmers extends AppCompatActivity implements farmersAdapter.itemClick {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Database database;
    ArrayList<farmerModel> arrayList=new ArrayList<>();
    farmersAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers);
        initUI();
        initData();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initData() {
        arrayList.addAll(database.fetchData());
        recyclerView.setAdapter(adapter);
        Log.d("data", new Gson().toJson(arrayList.get(0)));
    }

    private void initUI() {
        database = new Database(this);
        database.getWritableDatabase();
        adapter=new farmersAdapter(farmers.this, arrayList);
        recyclerView=findViewById(R.id.recyclerView);
        toolbar=findViewById(R.id.tool_bar);
        adapter.setOnclick(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onItemClick(farmerModel model) {
        SharedPreferences sharedPreferences=getSharedPreferences(constants.SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(constants.DATA, new Gson().toJson(model));
        editor.apply();
        new viewFarmer().show(getSupportFragmentManager(),"View Farmer");
    }
}
