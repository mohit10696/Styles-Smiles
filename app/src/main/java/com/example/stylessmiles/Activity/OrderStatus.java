package com.example.stylessmiles.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.OrderAdapterBusiness;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {
    List<OrderModel> orders;
    SharedPreferences mPrefs;
    String email;
    private DatabaseReference databaseReference;
    RecyclerView rv_order;
    LinearLayoutManager layoutManager;
    FirebaseAuth firebaseAuth;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        mPrefs = getPreferences(MODE_PRIVATE);
        orders = new ArrayList<OrderModel>();
        email = centralStore.getInstance().salonMail;
        getSupportActionBar().setTitle(email);
        rv_order = findViewById(R.id.ordersRecycle);
        firebaseAuth = FirebaseAuth.getInstance();
        fetchOrder();

    }

    private void fetchOrder() {
        String parts[] = email.toLowerCase().split("@");
        if (parts.length < 2) {
            Toast.makeText(this, email+"Some error occurred"+parts.length, Toast.LENGTH_SHORT).show();
            return;
        }
        String salonName = parts[0];

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
//                    Toast.makeText(getContext(),snapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                int i = 0;
                for (DataSnapshot value : snapshot.getChildren()) {
//                    Toast.makeText(getApplicationContext(), "+1"+i, Toast.LENGTH_SHORT).show();
                    if(value.child("order").child("saloonname").getValue().toString().toLowerCase().contains(salonName)){
//                    Toast.makeText(OrderStatus.this, value.child("order").getValue().toString(), Toast.LENGTH_SHORT).show();
                        OrderModel orderModel = new OrderModel();
//                    value.getValue(orderModel);
                        orders.add(value.getValue(OrderModel.class));
                        orders.get(i).setOrderNo(value.getKey());
                        i++;
                    }

                }
                praseOrders();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void praseOrders() {
        OrderAdapterBusiness adapter = new OrderAdapterBusiness(orders, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(layoutManager);
        rv_order.setAdapter(adapter);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenubussiness, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut(); //End user session
            startActivity(new Intent(OrderStatus.this, Login.class)); //Go back to home page
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}