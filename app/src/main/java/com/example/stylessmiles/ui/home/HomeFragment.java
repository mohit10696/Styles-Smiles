package com.example.stylessmiles.ui.home;

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
import com.example.stylessmiles.adpater.SaloonListAdapter;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.SaloonModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        saloons = new ArrayList<SaloonModel>();
        mDatabase.child("SaloonList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.e("Database", "onComplete: "+task.getResult().getKey() );
                for(DataSnapshot dataSnapshot:task.getResult().getChildren()){
                    saloons.add(new SaloonModel(dataSnapshot.getKey(),dataSnapshot.child("Address").getValue().toString(),dataSnapshot.child("City").getValue().toString(),dataSnapshot.child("Image").getValue().toString()));
                    saloonRecycle = root.findViewById(R.id.recycle_saloon);
                    SaloonListAdapter adapter = new SaloonListAdapter(saloons,getActivity());
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