package com.alome.tellerium.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alome.tellerium.Models.farmerModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;
import com.bumptech.glide.Glide;
import com.github.bkhezry.mapdrawingtools.model.DataModel;
import com.github.bkhezry.mapdrawingtools.model.DrawingOption;
import com.github.bkhezry.mapdrawingtools.model.DrawingOptionBuilder;
import com.github.bkhezry.mapdrawingtools.ui.MapsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class viewFarmer extends BottomSheetDialogFragment {
    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int REQUEST_CODE = 10;
    View view;
    EditText fullName, location;
    Uri uri;
    Button register;
    Database database;
    Toolbar toolbar;
    int localCount;
    ImageView icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.view_farmer, container, false);
        initUI();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withContext(getContext())
                        .withPermissions(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            AlertDialog.Builder alertDialog= new AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("Click on various Point of the Map to Draw Polygons")
                                    .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DrawingOption.DrawingType currentDrawingType = DrawingOption.DrawingType.POLYGON;
                                            Intent intent =
                                                    new DrawingOptionBuilder()
                                                            .withLocation(35.744502, 51.368966)
                                                            .withMapZoom(14)
                                                            .withFillColor(Color.argb(60, 0, 0, 255))
                                                            .withStrokeColor(Color.argb(100, 255, 0, 0))
                                                            .withStrokeWidth(3)
                                                            .withRequestGPSEnabling(false)
                                                            .withDrawingType(currentDrawingType)
                                                            .build(getContext());
                                            startActivityForResult(intent, REQUEST_CODE);
                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();



                        }else{
                            Toast.makeText(getContext(), "Please Grant Permission to access Map", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();

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
        toolbar=view.findViewById(R.id.tool_bar);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            DataModel dataModel =
                    data.getExtras().getParcelable(MapsActivity.POINTS);
            LatLng[] points=dataModel.getPoints();
            Toast.makeText(getContext(), "Polygon Drawn!", Toast.LENGTH_SHORT).show();
        }
    }
}
