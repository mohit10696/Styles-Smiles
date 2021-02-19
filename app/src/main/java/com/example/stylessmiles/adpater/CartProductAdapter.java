package com.example.stylessmiles.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.Activity.Cart;
import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
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
        holder.tv_productname.setText(centralStore.getInstance().capitalize(productModels.get(position).getName()));
        holder.tv_price.setText("Rs. " + productModels.get(position).getPrice());
        Picasso.get().load(productModels.get(position).getImage()).into(holder.iv_product);
        holder.tv_quantity.setText(String.valueOf(centralStore.getInstance().cart.getProductQuantity(position)));
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(centralStore.getInstance().cart.getProductQuantity().size()), Toast.LENGTH_SHORT).show();
                if (centralStore.getInstance().cart.getProductQuantity(position) == 10) {
                    Toast.makeText(context, "You can't order more then 10 items at a time", Toast.LENGTH_SHORT).show();
//                    ((Cart)context).updateTotalPrice();
                } else
                {
                    holder.tv_quantity.setText(String.valueOf(centralStore.getInstance().cart.updateQunatity(position, +1)));
                    ((Cart)context).updateTotalPrice();
                }
            }
        });
        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (centralStore.getInstance().cart.getProductQuantity(position) == 1) {
//                    Toast.makeText(context, "Minimum limit reached", Toast.LENGTH_SHORT).show();
                    centralStore.getInstance().cart.removeProduct(position);
                    ((Cart)context).updateTotalPrice();
                    notifyDataSetChanged();
                } else
                {
                   holder.tv_quantity.setText(String.valueOf(centralStore.getInstance().cart.updateQunatity(position, -1)));
                    ((Cart)context).updateTotalPrice();
                }
            }
        });

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
        public TextView tv_quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_cart);
            tv_productname = itemView.findViewById(R.id.tv_name);
            tv_quantity = itemView.findViewById(R.id.tv_qunatitiy);
            tv_price = itemView.findViewById(R.id.tv_price);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);

        }
    }

}
