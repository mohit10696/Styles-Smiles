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
import com.example.stylessmiles.model.SaloonModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    List<SaloonModel> saloons;
    LinearLayoutManager layoutManager;
    @BindView(R.id.recycle_saloon)
    RecyclerView saloonRecycle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        saloons = new ArrayList<SaloonModel>();
        mDatabase.child("SaloonList").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {

            @Override
            public void onSuccess(DataSnapshot dataSnapshot2) {
                Log.e("Database", "onComplete: " + dataSnapshot2.getKey());
                for (DataSnapshot dataSnapshot : dataSnapshot2.getChildren()) {
                    saloons.add(new SaloonModel(dataSnapshot.getKey(), dataSnapshot.child("Address").getValue().toString(), dataSnapshot.child("City").getValue().toString(), dataSnapshot.child("Image").getValue().toString()));
                    saloonRecycle = root.findViewById(R.id.recycle_saloon);
                    SaloonListAdapter adapter = new SaloonListAdapter(saloons, getActivity());
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    saloonRecycle.setLayoutManager(layoutManager);
                    saloonRecycle.setAdapter(adapter);
                    centralStore.getInstance().setSaloons(saloons);
                }
            }


        });
        return root;
    }
}