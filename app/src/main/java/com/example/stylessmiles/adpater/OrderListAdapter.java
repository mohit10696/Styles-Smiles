package com.example.stylessmiles.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.CartModel;
import com.example.stylessmiles.model.ProductModel;
import com.example.stylessmiles.model.ServicesModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    List<ProductModel> products;
    List<ServicesModel> services;
    Context context;
    List<Integer> productQ;
    CartModel cart;

    public OrderListAdapter(CartModel cart, Context context) {
        this.context = context;
        this.cart = cart;
        this.productQ = cart.getProductQuantity();
        this.products = cart.getProducts();
        this.services = cart.getServices();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_item, parent, false);
        OrderListAdapter.ViewHolder viewHolder = new OrderListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < products.size()) {
            Picasso.get().load(products.get(position).getImage()).into(holder.iv_product);
            holder.tv_productname.setText(products.get(position).getName() + " x " +productQ.get(position).toString());
            holder.tv_productprice.setText(products.get(position).getPrice());
        } else {
            position = position - products.size();
            holder.tv_productname.setText(services.get(position).getName());
            holder.tv_productprice.setText(services.get(position).getPrice());
            Picasso.get().load(services.get(position).getImage()).into(holder.iv_product);
        }
    }

    @Override
    public int getItemCount() {
        return products.size() + services.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_product;
        TextView tv_productname;
        TextView tv_productprice;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.tv_product);
            tv_productname = itemView.findViewById(R.id.tv_productname);
            tv_productprice = itemView.findViewById(R.id.tv_productprice);
        }
    }
}
