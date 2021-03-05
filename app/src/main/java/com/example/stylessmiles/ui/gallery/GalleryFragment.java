package com.example.stylessmiles.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.OrderAdapter;
import com.example.stylessmiles.adpater.SaloonListAdapter;
import com.example.stylessmiles.model.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    List<OrderModel> orders;
    RecyclerView rv_order;
    LinearLayoutManager layoutManager;
    View root;
    private DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        orders = new ArrayList<OrderModel>();
        rv_order = root.findViewById(R.id.ordersRecycle);
        fetchOrder();
        return root;
    }

    private void fetchOrder() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
//                    Toast.makeText(getContext(),snapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                int i = 0;
                for(DataSnapshot value : snapshot.getChildren()){
                    OrderModel orderModel = new OrderModel();
//                    value.getValue(orderModel);
                    orders.add(value.getValue(OrderModel.class));
                    orders.get(i).setOrderNo(value.getKey());
                    i++;
                    praseOrders();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void praseOrders() {
        OrderAdapter adapter = new OrderAdapter(orders, getActivity());
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(layoutManager);
        rv_order.setAdapter(adapter);
    }
}