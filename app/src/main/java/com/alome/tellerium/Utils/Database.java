package com.alome.tellerium.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alome.tellerium.Models.farmerModel;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context)
    {
        super(context, "farmers_main.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEmp="create table farmers(id text,name text,location text, image_url text, local integer)";
        db.execSQL(tableEmp);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertData(String id,String name, String location,  String image_url, int isLocal )
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("location",location);
        values.put("image_url", image_url);
        values.put("local", isLocal);
        sqLiteDatabase.insert("farmers",null,values);
    }
    public ArrayList<farmerModel> fetchData()
    {
        ArrayList<farmerModel>datas=new ArrayList<>();
        farmerModel model;
        String fetchdata="select * from farmers";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        if(cursor.moveToFirst()){
            do
            {
                if (Integer.parseInt(cursor.getString(4))==0){
                    model= new farmerModel(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),false);

                }else{
                    model= new farmerModel(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),true);

                }
                datas.add(model);

            } while (cursor.moveToNext());
        }
        return datas;
    }
}