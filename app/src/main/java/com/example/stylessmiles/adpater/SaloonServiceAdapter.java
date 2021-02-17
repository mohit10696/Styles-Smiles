package com.example.stylessmiles.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.ServicesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SaloonServiceAdapter extends RecyclerView.Adapter<SaloonServiceAdapter.ViewHolder> {

    List<ServicesModel> servicesModels;
    Context context;
    String saloonname;

    public SaloonServiceAdapter(List<ServicesModel> servicesModels, Context context, String saloonname) {
        this.servicesModels = servicesModels;
        this.context = context;
        this.saloonname = saloonname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_products, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_servicename.setText(servicesModels.get(position).getName().substring(0, 1).toUpperCase() + servicesModels.get(position).getName().substring(1));
        holder.tv_price.setText("Rs :" + servicesModels.get(position).getPrice());
        Picasso.get().load(servicesModels.get(position).getImage()).into(holder.iv_product);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                if (centralStore.getInstance().cart.setSaloon(saloonname)) {
                    holder.btn_add.setText("ADDED");
                    holder.btn_add.setEnabled(false);
                    Toast.makeText(context, "added to cart", Toast.LENGTH_SHORT).show();
                    centralStore.getInstance().cart.addService(servicesModels.get(position));
                    centralStore.getInstance().synccart();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product;
        public TextView tv_servicename;
        public TextView tv_price;
        public Button btn_add;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_product = (ImageView) itemView.findViewById(R.id.iv_product);
            this.tv_servicename = (TextView) itemView.findViewById(R.id.tv_servicename);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            this.btn_add = (Button) itemView.findViewById(R.id.btn_add);
        }
    }
}
