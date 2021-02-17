package com.example.stylessmiles.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.CartProductAdapter;
import com.example.stylessmiles.adpater.CartServiceAdapter;
import com.example.stylessmiles.centralStore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cart extends AppCompatActivity {

    @BindView(R.id.rv_services)
    RecyclerView serviceRecycle;

    @BindView(R.id.rv_product)
    RecyclerView productRecycle;

    @BindView(R.id.tv_totalPrice)
    TextView tv_totalPrice;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_saloonname)
    TextView tv_saloonname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");
        loadData();
    }

    private void loadData() {
        LinearLayoutManager layoutManager;
        CartServiceAdapter adapter = new CartServiceAdapter(centralStore.cart.getServices(), getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        serviceRecycle.setLayoutManager(layoutManager);
        serviceRecycle.setAdapter(adapter);
        LinearLayoutManager layoutManager2;
        CartProductAdapter adapter2 = new CartProductAdapter(centralStore.cart.getProducts(), getApplicationContext());
        layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        productRecycle.setLayoutManager(layoutManager2);
        productRecycle.setAdapter(adapter2);
        tv_totalPrice.setText("Rs. "+String.valueOf(centralStore.cart.getTotalPrice()));
        tv_address.setText("Address: "+centralStore.getUser().getAddress());
        tv_saloonname.setText("Salon: "+centralStore.cart.getSaloonname());
    }
}