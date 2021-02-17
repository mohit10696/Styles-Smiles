package com.example.stylessmiles.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.SaloonProductAdapter;
import com.example.stylessmiles.adpater.SaloonServiceAdapter;
import com.example.stylessmiles.model.ProductModel;
import com.example.stylessmiles.model.SaloonDetailModel;
import com.example.stylessmiles.model.ServicesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaloonProfileActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Intent intent;
    private String saloonName;
    private SaloonDetailModel SaloonData;
    private List<ProductModel> productList;


    @BindView(R.id.tv_shopname)
    TextView tv_shopname;

    @BindView(R.id.tv_shop_category)
    TextView tv_shop_category;

    @BindView(R.id.tv_storeaddress)
    TextView tv_storeaddress;

    @BindView(R.id.rv_services)
    RecyclerView serviceRecycle;

    @BindView(R.id.rv_product)
    RecyclerView productRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_profile);
        try {
            // this.getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
        }

        ButterKnife.bind(this);
        intent = getIntent();
        saloonName = intent.getExtras().getString("saloonname");
        getSaloonServiceData();
        getSaloonProductData();
    }

    private void getSaloonProductData() {
        productList = new ArrayList<ProductModel>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Products").get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                          @Override
                                          public void onSuccess(DataSnapshot dataSnapshot) {
                                              for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                  Log.e(data.child("Name").getValue().toString(), "onSuccess: ");
                                                  productList.add(new ProductModel(data.child("Name").getValue().toString(), data.child("Price").getValue().toString(), data.child("Image").getValue().toString()));
                                              }
//                                               Log.e(task.toString(), "onSuccess: " );
                                              loadProduct();
                                          }


//                                           public void onSuccess(@NonNull Task<DataSnapshot> task) {
//
//                                           }
                                      }
                );
    }


    private void getSaloonServiceData() {
        List<ServicesModel> servicesModels = new ArrayList<ServicesModel>();
        SaloonData = new SaloonDetailModel();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("SaloonList").child(saloonName).get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                          @Override
                                          public void onSuccess(DataSnapshot dataSnapshot2) {
                                              SaloonData.setAddress(dataSnapshot2.child("Address").getValue().toString());
                                              SaloonData.setName(dataSnapshot2.child("Name").getValue().toString());
                                              SaloonData.setImage(dataSnapshot2.child("Image").getValue().toString());
                                              SaloonData.setCity(dataSnapshot2.child("City").getValue().toString());
                                              for (DataSnapshot dataSnapshot : dataSnapshot2.child("Services").getChildren()) {
                                                  Log.e("Services", "onComplete: " + dataSnapshot.child("Service").getValue().toString());
                                                  servicesModels.add(new ServicesModel(dataSnapshot.child("Service").getValue().toString(), dataSnapshot.child("Price").getValue().toString(), dataSnapshot.child("Image").getValue().toString()));
                                              }
                                              SaloonData.setServicesModels(servicesModels);
                                              LoadData();
                                          }
                                      }
                );
    }

    private void LoadData() {
        LinearLayoutManager layoutManager;
        this.tv_shopname.setText(this.SaloonData.getName());
        this.tv_shop_category.setText(this.SaloonData.getCity());
        this.tv_storeaddress.setText(this.SaloonData.getAddress());
        getSupportActionBar().setTitle(this.SaloonData.getName());
        SaloonServiceAdapter adapter = new SaloonServiceAdapter(SaloonData.getServicesModels(), getApplicationContext(),this.SaloonData.getName());
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        serviceRecycle.setLayoutManager(layoutManager);
        serviceRecycle.setAdapter(adapter);
    }

    private void loadProduct() {
        LinearLayoutManager layoutManager;
        SaloonProductAdapter adapter = new SaloonProductAdapter(productList, getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        productRecycle.setLayoutManager(layoutManager);
        productRecycle.setAdapter(adapter);
    }
}