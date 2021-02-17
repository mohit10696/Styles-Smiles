package com.example.stylessmiles.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    List<ProductModel> productModels;
    Context context;

    public CartProductAdapter(List<ProductModel> productModels, Context context) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_cart, parent, false);
        CartProductAdapter.ViewHolder viewHolder = new CartProductAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_productname.setText(productModels.get(position).getName());
        holder.tv_price.setText(productModels.get(position).getPrice());
        Picasso.get().load(productModels.get(position).getImage()).into(holder.iv_product);
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product;
        public TextView tv_productname;
        public TextView tv_price;
        public ImageView btn_plus;
        public ImageView btn_minus;
        public TextView quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_cart);
            tv_productname = itemView.findViewById(R.id.tv_name);
            quantity = itemView.findViewById(R.id.tv_qunatitiy);
            tv_price = itemView.findViewById(R.id.tv_price);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);

        }
    }

}
