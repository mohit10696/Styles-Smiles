package com.example.stylessmiles.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stylessmiles.R;
import com.example.stylessmiles.adpater.SaloonListAdapter;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.CartModel;
import com.example.stylessmiles.model.ProductModel;
import com.example.stylessmiles.model.SaloonModel;
import com.example.stylessmiles.model.ServicesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    List<SaloonModel> saloons;
    LinearLayoutManager layoutManager;
    @BindView(R.id.recycle_saloon)
    RecyclerView saloonRecycle;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        saloons = new ArrayList<SaloonModel>();

        loadSaloon();

        return root;
    }

    private void loadSaloon() {
        mDatabase.child("SaloonList").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                Log.e("Database", "onComplete: " + dataSnapshot2.getKey());
                for (DataSnapshot dataSnapshot : dataSnapshot2.getChildren()) {
                    saloons.add(new SaloonModel(dataSnapshot.getKey(), dataSnapshot.child("Address").getValue().toString(), dataSnapshot.child("City").getValue().toString(), dataSnapshot.child("Image").getValue().toString()));
                    saloonRecycle = root.findViewById(R.id.recycle_saloon);
                    SaloonListAdapter adapter = new SaloonListAdapter(saloons, getActivity());
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    saloonRecycle.setLayoutManager(layoutManager);
                    saloonRecycle.setAdapter(adapter);
                    centralStore.getInstance().setSaloons(saloons);
                    loadCart();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });
    }

    private void loadCart() {
        CartModel cartModel = new CartModel();
        mDatabase.child("user").child(centralStore.getInstance().getUser().getEmail().replace('.', '_')).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                        if (dataSnapshot2.hasChild("cart")) {
                            DataSnapshot cart = dataSnapshot2.child("cart");
                            if (cart.hasChild("productQuantity")) {
                                DataSnapshot productQ = cart.child("productQuantity");
                                for (DataSnapshot data : productQ.getChildren()) {
                                    List<Integer> prodQ = new ArrayList<Integer>();
                                    prodQ.add(Integer.parseInt(data.getValue().toString()));
                                    cartModel.setProductQuantity(prodQ);
                                }
                            }
                            if (cart.hasChild("products")) {
                                DataSnapshot product = cart.child("products");
                                for (DataSnapshot data : product.getChildren()) {
                                    List<ProductModel> productModels = new ArrayList<ProductModel>();
                                    productModels.add(new ProductModel(data.child("name").getValue().toString(), data.child("price").getValue().toString(), data.child("image").getValue().toString()));
                                    cartModel.setProducts(productModels);
                                }
                            }
                            if (cart.hasChild("services")) {
                                DataSnapshot services = cart.child("services");
                                for (DataSnapshot data : services.getChildren()) {
                                    List<ServicesModel> servicesModel = new ArrayList<ServicesModel>();
                                    servicesModel.add(new ServicesModel(data.child("name").getValue().toString(), data.child("price").getValue().toString(), data.child("image").getValue().toString()));
                                    cartModel.setServices(servicesModel);
                                }
                            }
                            if (cart.hasChild("saloonname")) {
                                cartModel.setSaloon(cart.child("saloonname").getValue().toString());
                            }
                            if (cart.hasChild("totalPrice")) {
                                cartModel.setTotalPrice(Integer.parseInt(cart.child("totalPrice").getValue().toString()));
                            }
                        }
                        centralStore.getInstance().cart = cartModel;
                        // Log.e("Cart loading", "onDataChange: "+cartModel.toString() );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }


                });
    }
}