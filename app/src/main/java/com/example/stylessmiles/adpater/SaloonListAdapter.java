package com.example.stylessmiles.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.Activity.SaloonProfileActivity;
import com.example.stylessmiles.R;
import com.example.stylessmiles.model.SaloonModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SaloonListAdapter extends RecyclerView.Adapter<SaloonListAdapter.ViewHolder> {
    private List<SaloonModel> SaloonList;
    private Context context;
    public SaloonListAdapter(List<SaloonModel> saloonList,Context context) {
        this.SaloonList = saloonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_saloon, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.saloonName.setText(SaloonList.get(position).getName());
        holder.saloonAddress.setText(SaloonList.get(position).getAddress());
        holder.row_saloonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaloonProfileActivity.class);
                intent.putExtra("saloonname",SaloonList.get(position).getName());
                context.startActivity(intent);
            }
        });
        Picasso.get().load(SaloonList.get(position).getImgurl()).into(holder.saloonImage);
    }

    @Override
    public int getItemCount() {
        return SaloonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView saloonImage;
        public TextView saloonName;
        public TextView saloonAddress;
        public RelativeLayout row_saloonList;
        public ViewHolder(View itemView) {
            super(itemView);
            this.saloonImage = (ImageView) itemView.findViewById(R.id.iv_saloon);
            this.saloonName = (TextView) itemView.findViewById(R.id.tv_saloonname);
            this.saloonAddress = (TextView) itemView.findViewById(R.id.tv_saloonaddress);
            this.row_saloonList = (RelativeLayout) itemView.findViewById(R.id.row_saloonList);
        }
    }
}
