package com.alome.tellerium.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alome.tellerium.Models.farmerModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.Random;

public class viewFarmer extends BottomSheetDialogFragment {
    private static final int PICK_IMAGE_REQUEST = 100;
    View view;
    EditText fullName, location;
    Uri uri;
    Button register;
    Database database;
    int localCount;
    ImageView icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.view_farmer, container, false);
        initUI();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


    private void initUI() {
        database = new Database(getContext());
        database.getWritableDatabase();
        fullName=view.findViewById(R.id.name);
        location=view.findViewById(R.id.location);
        register=view.findViewById(R.id.reg);
        icon=view.findViewById(R.id.avatar);

        SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF,Context.MODE_PRIVATE);
        String data=sharedPreferences.getString(constants.DATA,"");
        farmerModel model=new Gson().fromJson(data, farmerModel.class);
        fullName.setText(model.getLoctaion().replace("\"", "").toUpperCase());
        location.setText(model.getId());
        if (model.isLocal()){
            icon.setImageURI(Uri.parse(model.getAvatar_url()));
        }else {
            Glide.with(getContext())
                    .load(model.getAvatar_url())
                    .placeholder(R.drawable.ic_profile_user)
                    .into(icon);
        }
    }

}
