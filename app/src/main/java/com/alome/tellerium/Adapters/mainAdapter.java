package com.alome.tellerium.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Models.mainModel;
import com.alome.tellerium.R;

import java.util.ArrayList;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.MyViewHolder> {
    Context context;
    ArrayList<mainModel> arrayList=new ArrayList<>();
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mainModel model=arrayList.get(position);
        holder.count.setText(String.valueOf(model.getCount()));
        holder.title.setText(model.getTitle());
        holder.icon.setImageResource(model.getIcon());

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
