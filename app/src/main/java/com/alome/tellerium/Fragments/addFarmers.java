package com.alome.tellerium.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Random;

public class addFarmers extends BottomSheetDialogFragment {
    View view;
    EditText fullName, location;
    Button register;
    Database database;
    int localCount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_new, container, false);
        initUI();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify()){
                    database.insertData(new Random(8).toString(),fullName.getText().toString(), location.getText().toString(), "");
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt(constants.LOCAL_COUNT, localCount+1);
                    editor.apply();
                    Toast.makeText(getContext(), "Farmer Registered Successfully!", Toast.LENGTH_SHORT).show();
                    dismiss();

                }
            }
        });
        return view;
    }

    private boolean verify() {
        boolean isValid;
        if (fullName.getText().toString().isEmpty()){
            fullName.setError("Required");
            isValid=false;
        }else if (location.getText().toString().isEmpty()){
            location.setError("Required");
            isValid=false;
        }else{
            isValid=true;
        }
        return isValid;
    }

    private void initUI() {
        database = new Database(getContext());
        database.getWritableDatabase();
        fullName=view.findViewById(R.id.name);
        location=location.findViewById(R.id.location);
        register=view.findViewById(R.id.reg);
        // fetch locally stored farmer

        SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
        localCount=sharedPreferences.getInt(constants.LOCAL_COUNT,0);
    }
}
