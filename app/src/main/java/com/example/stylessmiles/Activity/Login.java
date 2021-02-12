package com.example.stylessmiles.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.usermodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    SharedPreferences mPrefs;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    FirebaseDatabase rootnode;
    SharedPreferences.Editor prefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setTheme(R.style.Theme_StylesSmiles);
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        mDatabase = rootnode.getReference("user");
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        if(firebaseAuth.getCurrentUser() != null ){
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            usermodel usermodel = gson.fromJson(json, usermodel.class);
            centralStore.getInstance().user = usermodel;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    public void loadSignup(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }


    public void login(View view) {
        if (et_email.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString().trim(), et_password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child(et_email.getText().toString().trim().replace('.','_')).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    usermodel usermodel = new usermodel();
                                    usermodel.setFirstname(task.getResult().child("firstname").getValue().toString());
                                    usermodel.setLastname(task.getResult().child("lastname").getValue().toString());
                                    usermodel.setEmail(task.getResult().child("email").getValue().toString());
                                    centralStore.getInstance().user = usermodel;
                                    Gson gson = new Gson();
                                    String json = gson.toJson(usermodel);
                                    prefsEditor.putString("user", json);
                                    prefsEditor.commit();
                                    Toast.makeText(Login.this, "Login successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }
                        else{
                            Toast.makeText(Login.this,"Invalid login id or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}