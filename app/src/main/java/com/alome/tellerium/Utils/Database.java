package com.alome.tellerium.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context)
    {
        super(context, "farmers_main.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEmp="create table farmers(id text,name text,location text, image_url text)";
        db.execSQL(tableEmp);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertData(String id,String name, String location,  String image_url)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("location",location);
        values.put("image_url", image_url);
        sqLiteDatabase.insert("farmers",null,values);
    }
    public ArrayList fetchData()
    {
        ArrayList<String>stringArrayList=new ArrayList<String>();
        String fetchdata="select * from farmers";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        if(cursor.moveToFirst()){
            do
            {
                stringArrayList.add(cursor.getString(0));
                stringArrayList.add(cursor.getString(1));
                stringArrayList.add(cursor.getString(2));
                stringArrayList.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }
}