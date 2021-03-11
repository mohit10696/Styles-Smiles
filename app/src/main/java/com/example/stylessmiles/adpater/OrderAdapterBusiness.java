package com.example.stylessmiles.adpater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.OrderModel;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class OrderAdapterBusiness extends RecyclerView.Adapter<OrderAdapterBusiness.ViewHolder> {

    List<OrderModel> order;
    Context context;
    LinearLayoutManager layoutManager;

    public OrderAdapterBusiness(List<OrderModel> order, Context context) {
        this.order = order;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_order_business, parent, false);
        OrderAdapterBusiness.ViewHolder viewHolder = new OrderAdapterBusiness.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("key", "onDataChange: " + order.get(position).getOrderNo());
        holder.tv_client_name.setText(order.get(position).getUsermodel().getFirstname() + " " + order.get(position).getUsermodel().getLastname());
        holder.tv_client_number.setText(order.get(position).getUsermodel().getContact());
        holder.tv_orderid.setText(order.get(position).getOrderNo());
        holder.tv_orderstatus.setText(order.get(position).getOrderStatus());
        holder.orderdate.setText(order.get(position).getOrderDate());
        holder.tv_appoitmentdate.setText(order.get(position).getAppoimentDate());
        holder.tv_address.setText(order.get(position).getUsermodel().getAddress());
        holder.tv_total.setText("Rs. " + String.valueOf(order.get(position).getOrder().getTotalPrice()));
        holder.btn_cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(order.get(position).getUsermodel().getFirstname() + " " + order.get(position).getUsermodel().getLastname())
                        .setMessage("Do you really want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
                                order.get(position).cancelOrder();
                                centralStore.getInstance().mDatabase.child("Order").child(order.get(position).getOrderNo()).setValue(order.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        holder.btn_cancelOrder.setText(order.get(position).getOrderStatus());
                                    }
                                });


                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
        if ((order.get(position).getOrderStatus().equals(centralStore.cancelOrder))) {
            holder.btn_orderStatusUpdate.setVisibility(View.GONE);
            holder.btn_cancelOrder.setVisibility(View.GONE);
        }
        if (order.get(position).getOrderStatus().equals(centralStore.confirmOrder)) {
            holder.btn_orderStatusUpdate.setText("Order Completed ? ");
            holder.btn_orderStatusUpdate.setBackgroundColor(Color.GREEN);
        } else if (order.get(position).getOrderStatus().equals(centralStore.completeOrder)) {
            holder.btn_cancelOrder.setVisibility(View.GONE);
            holder.btn_orderStatusUpdate.setText("Already Completed");
            holder.btn_orderStatusUpdate.setBackgroundColor(Color.GRAY);
            holder.btn_orderStatusUpdate.setEnabled(false);
        }
        holder.btn_orderStatusUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(order.get(position).getUsermodel().getFirstname() + " " + order.get(position).getUsermodel().getLastname())
                        .setMessage("Are you sure ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (order.get(position).updateStatus().equals(centralStore.confirmOrder)) {
                                    holder.btn_orderStatusUpdate.setText("Order Completed ? ");
                                    holder.btn_orderStatusUpdate.setBackgroundColor(Color.GREEN);
                                } else if (order.get(position).updateStatus().equals(centralStore.completeOrder)) {
                                    holder.btn_orderStatusUpdate.setText("Already Completed");
                                    holder.btn_orderStatusUpdate.setBackgroundColor(Color.GRAY);
                                    holder.btn_orderStatusUpdate.setEnabled(false);
                                } else {
                                    Toast.makeText(context, "You cant not perform this actions now!!", Toast.LENGTH_SHORT).show();
                                }
                                centralStore.getInstance().mDatabase.child("Order").child(order.get(position).getOrderNo()).setValue(order.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                        holder.btn_orderStatusUpdate.setText(order.get(position).getOrderStatus());
                                    }
                                });

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
//                Toast.makeText(context, order.get(position).orderStatus, Toast.LENGTH_SHORT).show();

            }
        });


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
        public TextView tv_orderid;
        public TextView tv_client_number;
        public TextView tv_client_name;
        public TextView tv_orderstatus;
        public TextView orderdate;
        public TextView tv_appoitmentdate;
        public TextView tv_address;
        public TextView tv_total;
        public RecyclerView rv_orders;
        public Button btn_cancelOrder;
        public Button btn_orderStatusUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_client_number = itemView.findViewById(R.id.tv_client_number);
            tv_client_name = itemView.findViewById(R.id.tv_client_name);
            tv_orderid = itemView.findViewById(R.id.tv_orderid);
            tv_orderstatus = itemView.findViewById(R.id.tv_orderstatus);
            orderdate = itemView.findViewById(R.id.orderdate);
            tv_appoitmentdate = itemView.findViewById(R.id.tv_appoitmentdate);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_total = itemView.findViewById(R.id.tv_total);
            rv_orders = itemView.findViewById(R.id.rv_orders);
            btn_cancelOrder = itemView.findViewById(R.id.btn_cancelOrder);
            btn_orderStatusUpdate = itemView.findViewById(R.id.btn_status);
        }
    }
}
