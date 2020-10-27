package com.alome.tellerium.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Models.mainModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Database;
import com.alome.tellerium.Utils.constants;

import java.util.ArrayList;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.MyViewHolder> {
    Context context;
    ArrayList<mainModel> arrayList=new ArrayList<>();
    SharedPreferences preferences;
    Database database;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    public mainAdapter(Context context, ArrayList<mainModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final mainModel model=arrayList.get(position);
        holder.count.setText(String.valueOf(model.getCount()));
        holder.title.setText(model.getTitle());
        holder.icon.setImageResource(model.getIcon());
        if (position==1){
            preferences=context.getSharedPreferences(constants.SHARED_PREF, Context.MODE_PRIVATE);
            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int count = preferences.getInt(constants.LOCAL_COUNT, 0);
                    handler.postDelayed(this,1000);
                    holder.count.setText(String.valueOf(count));
                }
            },1000);
        }

        if (position==0){

            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    database = new Database(context);
                    database.getWritableDatabase();
                    holder.count.setText(String.valueOf(database.fetchData().size()));
                }
            },1000);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView title,count;
        ImageView icon;
        public MyViewHolder(@NonNull View v) {
            super(v);
            title=v.findViewById(R.id.title);
            count=v.findViewById(R.id.count);
            icon=v.findViewById(R.id.icon);
        }
    }
}
