package com.alome.tellerium.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Activities.Login;
import com.alome.tellerium.Activities.farmers;
import com.alome.tellerium.Adapters.mainAdapter;
import com.alome.tellerium.Listeners.RecyclerItemClickListener;
import com.alome.tellerium.Models.mainModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.Helper;
import com.alome.tellerium.Utils.constants;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class Dashboard extends Fragment implements PopupMenu.OnMenuItemClickListener {
    mainAdapter adapter;
    Helper helper;
    ArrayList<mainModel> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    ImageView menu;
    View view;
    Database database;
    PopupMenu.OnMenuItemClickListener listener;
    SharedPreferences preferences;
    View v2;
    private int count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment, container, false);
        initUI();
//        showTutorial(menu,v2,recyclerView);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
        mainModel model=new mainModel(10,"Farmers", R.drawable.ic_farmer);
        arrayList.add(model);
        model=new mainModel(count,"Add Farmer", R.drawable.ic_add_group);
        arrayList.add(model);
        recyclerView.setAdapter(adapter);
        boolean isLoaded=preferences.getBoolean(constants.DATA_LOADED,false);
        if (!isLoaded){
            loadData();
        }else{
            Log.d(constants.TAG, "Data already loaded and stored locally...");
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getContext(), farmers.class));
                        break;
                    case 1:
                        new addFarmers().show(getFragmentManager(),"Add farmers");
                        break;

                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view;
    }

    private void initUI() {
        v2=view.findViewById(R.id.add);
        menu=view.findViewById(R.id.menu);
        helper=new Helper(getContext());
        recyclerView=view.findViewById(R.id.recyclerView);
        adapter=new mainAdapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        database = new Database(getContext());
        database.getWritableDatabase();
        preferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
        final Handler handler=new Handler();
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
                 count=sharedPreferences.getInt(constants.LOCAL_COUNT,0);
                 handler.postDelayed(this,1000);
             }
         },1000);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.log_out:
                final IOSDialog dialog= new IOSDialog.Builder(getActivity())
                        .setMessageContent("Please wait...")
                        .setCancelable(false)
                        .show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        helper.logout();
                        startActivity(new Intent(getContext(), Login.class));
                        getActivity().finish();
                    }
                }, 2000);

                break;
        }
        return true;
    }


    public void loadData(){
        final IOSDialog iosDialog= new IOSDialog.Builder(getContext())
                .setMessageContent("Please wait..")
                .setCancelable(false)
                .show();
        Ion.with(getContext())
                .load(constants.BASE_URL)
                .setTimeout(10000)
                .addQuery("method", "GET")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        iosDialog.dismiss();
                        if (result==null){
                            Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("Response", result.toString());
                            JsonObject object=result.getAsJsonObject("data");

                            JsonArray c = object.getAsJsonArray("farmers");
                            Log.d("JsonArray", c.toString());
                            for (int a =0; a<c.size(); a++){
                                JsonObject obj=c.get(a).getAsJsonObject();
                                Log.d("DataResult", obj.get("reg_no").getAsString());
                                database.insertData(obj.get("farmer_id").getAsString(), obj.get("first_name")+" "+obj.get("surname"), obj.get("address").getAsString(), obj.get("passport_photo").getAsString());
                               if (database.fetchData().size()>0){
                                   SharedPreferences.Editor editor=preferences.edit();
                                   editor.putBoolean(constants.DATA_LOADED,true);
                                   editor.apply();
                               }
                            }
                        }
                    }
                });
    }

//    private void showTutorial(ImageView v1, View v2, RecyclerView v3){
//        ShowcaseConfig config = new ShowcaseConfig();
//        config.setDelay(500); // half second between each showcase view
//
//        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), constants.SHARED_PREF);
//
//        sequence.setConfig(config);
//
//        sequence.addSequenceItem(v1,
//                "Click here to view more option", "Next");
//
//
//        sequence.addSequenceItem(v3,
//                "Click on <b>Farmers</b> to view list of Farmers", "GOT IT");
//
//        sequence.start();
//    }
}
