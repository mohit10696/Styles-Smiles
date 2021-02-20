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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.OrderModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<OrderModel> order;
    Context context;
    LinearLayoutManager layoutManager;

    public OrderAdapter(List<OrderModel> order, Context context) {
        this.order = order;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_order, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_orderid.setText(order.get(position).getOrderNo());
        holder.tv_orderstatus.setText(order.get(position).getOrderStatus());
        holder.orderdate.setText(order.get(position).getOrderDate());
        holder.tv_appoitmentdate.setText(order.get(position).getAppoimentDate());
        holder.tv_address.setText(centralStore.getInstance().getUser().getAddress());
        holder.tv_total.setText(String.valueOf(order.get(position).getOrder().getTotalPrice()));

        holder.btn_cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.get().load(centralStore.getSaloonImage(order.get(position).getOrder().getSaloonname())).into(holder.iv_saloon);

        OrderListAdapter adapter = new OrderListAdapter(order.get(position).getOrder(), context);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.rv_orders.setLayoutManager(layoutManager);
        holder.rv_orders.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_saloon;
        public TextView tv_orderid;
        public TextView tv_orderstatus;
        public TextView orderdate;
        public TextView tv_appoitmentdate;
        public TextView tv_address;
        public TextView tv_total;
        public RecyclerView rv_orders;
        public Button btn_cancelOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_saloon = itemView.findViewById(R.id.iv_saloon);
            tv_orderid = itemView.findViewById(R.id.tv_orderid);
            tv_orderstatus = itemView.findViewById(R.id.tv_orderstatus);
            orderdate = itemView.findViewById(R.id.orderdate);
            tv_appoitmentdate = itemView.findViewById(R.id.tv_appoitmentdate);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_total = itemView.findViewById(R.id.tv_total);
            rv_orders = itemView.findViewById(R.id.rv_orders);
            btn_cancelOrder = itemView.findViewById(R.id.btn_cancelOrder);
        }
    }
}
