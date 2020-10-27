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

import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Random;

public class addFarmers extends BottomSheetDialogFragment {
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
        view=inflater.inflate(R.layout.add_new, container, false);
        initUI();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify()){
                    database.insertData(new Random(8).toString(),fullName.getText().toString(), location.getText().toString(), uri.toString(),1);
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt(constants.LOCAL_COUNT, localCount+1);
                    editor.apply();
                    Toast.makeText(getContext(), "Farmer Registered Successfully!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        return view;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] extraMimeTypes = {"image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    private boolean verify() {
        boolean isValid;
        if (fullName.getText().toString().isEmpty()){
            fullName.setError("Required");
            isValid=false;
        }else if (location.getText().toString().isEmpty()){
            location.setError("Required");
            isValid=false;
        }else if (uri==null){
            Toast.makeText(getContext(), "Please select Avatar", Toast.LENGTH_SHORT).show();
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
        location=view.findViewById(R.id.location);
        register=view.findViewById(R.id.reg);
        icon=view.findViewById(R.id.avatar);

        // fetch locally stored farmer
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
        localCount=sharedPreferences.getInt(constants.LOCAL_COUNT,0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST){
            uri=data.getData();
            icon.setImageURI(data.getData());
        }
    }
}
