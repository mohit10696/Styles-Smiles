package com.example.stylessmiles.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.stylessmiles.Activity.MainActivity;
import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.ContactusModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ContactusFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_contactus)
    EditText et_contactus;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    View root;
    FirebaseDatabase rootnode;
    DatabaseReference mDatabase;
    DataSnapshot userdata;
    String firstname;
    String lastname;
    String email;
    String contactus;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences mPrefs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(root);
        btn_submit = root.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        et_name = root.findViewById(R.id.et_name);
        et_email = root.findViewById(R.id.et_email);
        et_contactus = root.findViewById(R.id.et_contactus);
        mPrefs = this.getActivity().getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        loaddata();
        return root;
    }

    private void loaddata() {
        rootnode = FirebaseDatabase.getInstance();
        mDatabase = rootnode.getReference("user/" + centralStore.getInstance().getUser().getEmail().replace('.', '_'));
        mDatabase.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                userdata = dataSnapshot;
                if (userdata.hasChild("email")) {
                    email = userdata.child("email").getValue().toString();
                    et_email.setText(userdata.child("email").getValue().toString());
                }
                if (userdata.hasChild("firstname") && userdata.hasChild("lastname")) {
                    firstname = userdata.child("firstname").getValue().toString();
                    lastname = userdata.child("lastname").getValue().toString();
                    et_name.setText(userdata.child("firstname").getValue().toString() + " " + userdata.child("lastname").getValue().toString());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: {
                updateData();
                break;
            }
            case R.id.btn_cancel: {
                startActivity(new Intent(root.getContext(), MainActivity.class));
            }
        }

    }

    private void updateData() {

        contactus = et_contactus.getText().toString();
        if(contactus.trim().length() < 5 ){
            Toast.makeText(root.getContext(), "Your message is too short to be recorded", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabase = rootnode.getReference();
        mDatabase.child("contactus").push().setValue(new ContactusModel(et_name.getText().toString(), et_email.getText().toString(), et_contactus.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(root.getContext(), "You message has been recorded our team will contact you soon!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(root.getContext(), MainActivity.class));
            }
        });
//        address = et_address.getText().toString();
//        pincode = et_pincode.getText().toString();
//        usermodel usermodel = new usermodel(firstname,lastname,address,contact,pincode,email);
//        centralStore.getInstance().setUser(usermodel);
//        mDatabase.setValue(usermodel).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getContext(), "Data successfully updated", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Gson gson = new Gson();
//        String json = gson.toJson(usermodel);
//        prefsEditor.putString("user", json);
//        prefsEditor.commit();
    }


}
