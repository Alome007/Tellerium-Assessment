package com.alome.tellerium.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Models.farmerModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class farmersAdapter extends RecyclerView.Adapter<farmersAdapter.myViewHolder> {
    ArrayList<farmerModel> arrayList=new ArrayList<>();
    Context context;
    itemClick onclick;
    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView count,name,id;
        ImageView icon;
        RelativeLayout relativeLayout;
        public myViewHolder(@NonNull View v) {
            super(v);
            count=v.findViewById(R.id.count);
            name=v.findViewById(R.id.name);
            id=v.findViewById(R.id.id);
            icon=v.findViewById(R.id.avatar);
            relativeLayout=v.findViewById(R.id.rela);
        }
    }


    public void setOnclick(itemClick onclick) {
        this.onclick = onclick;
    }

    public  farmersAdapter(Context context, ArrayList<farmerModel> arrayList){
        this.context= context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.farmers_item, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      final farmerModel model=arrayList.get(position);
        holder.name.setText(model.getLoctaion().replace("\"", "").toUpperCase());
        holder.id.setText("Farmer 00"+position);
        holder.count.setText("0 Farm");
        if (model.isLocal()){
            holder.icon.setImageURI(Uri.parse(model.getAvatar_url()));
        }else {
            Glide.with(context)
                    .load(constants.IMAGE_BASE_URL+model.getAvatar_url())
                    .into(holder.icon);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onItemClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  interface itemClick{
        void onItemClick(farmerModel model);
    }

}
