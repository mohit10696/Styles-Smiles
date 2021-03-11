package com.example.stylessmiles.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kloadingspin.KLoadingSpin;
import com.example.stylessmiles.R;
import com.example.stylessmiles.centralStore;
import com.example.stylessmiles.model.usermodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.LoadingSpin)
    KLoadingSpin loadingspin;
    SharedPreferences mPrefs;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    FirebaseDatabase rootnode;
    SharedPreferences.Editor prefsEditor;
    String logintype = "";
    String Email_regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

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
//        Toast.makeText(this, "loagin ma avi", Toast.LENGTH_SHORT).show();
        prefsEditor = mPrefs.edit();
//        && logintype.equals("business")
        if (firebaseAuth.getCurrentUser() != null) {
            if (!mPrefs.getString("SalonMail", "").equals("") && mPrefs.getString("logintype", "").equals("business")) {

                centralStore.getInstance().salonMail = mPrefs.getString("salonmail", "");
//                Toast.makeText(this,centralStore.getInstance().salonMail  , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, OrderStatus.class));
                finish();
            } else {
                Gson gson = new Gson();
                String json = mPrefs.getString("user", "");
                usermodel usermodel = gson.fromJson(json, usermodel.class);
                centralStore.getInstance().user = usermodel;
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        }

    }


    public void loadSignup(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }


    public void login(View view) {
        logintype = "user";
        prefsEditor.putString("logintype", logintype);
        prefsEditor.commit();
        if (et_email.getText().toString().trim().isEmpty() || !Pattern.matches(Email_regex, et_email.getText().toString().trim())) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_password.getText().toString().trim().isEmpty() || et_password.getText().toString().trim().length() < 6) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_email.getText().toString().trim().toLowerCase().contains("salon.com")) {
            Toast.makeText(this, "Please login as business account", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingspin.setIsVisible(true);
        loadingspin.startAnimation();
        firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString().trim(), et_password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase.child(et_email.getText().toString().trim().replace('.', '_')).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    usermodel usermodel = new usermodel();
                                    if (snapshot.hasChild("firstname"))
                                        usermodel.setFirstname(snapshot.child("firstname").getValue().toString());
                                    if (snapshot.hasChild("lastname"))
                                        usermodel.setLastname(snapshot.child("lastname").getValue().toString());
                                    if (snapshot.hasChild("email"))
                                        usermodel.setEmail(snapshot.child("email").getValue().toString());
                                    if (snapshot.hasChild("address"))
                                        usermodel.setAddress(snapshot.child("address").getValue().toString());
                                    centralStore.getInstance().user = usermodel;
                                    Gson gson = new Gson();
                                    String json = gson.toJson(usermodel);
                                    prefsEditor.putString("user", json);
                                    prefsEditor.commit();
                                    loadingspin.stopAnimation();
                                    loadingspin.setIsVisible(false);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            loadingspin.stopAnimation();
                            loadingspin.setIsVisible(false);
                            Toast.makeText(Login.this, "Invalid login id or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }


    public void loginAsBusiness(View view) {
        if (et_email.getText().toString().trim().isEmpty() || !Pattern.matches(Email_regex, et_email.getText().toString().trim())) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (et_password.getText().toString().trim().isEmpty() || et_password.getText().toString().trim().length() < 6) {
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }
        logintype = "business";
        prefsEditor.putString("logintype", logintype);
        prefsEditor.commit();
        String et_email = this.et_email.getText().toString();
        String et_password = this.et_password.getText().toString();
        String[] parts = et_email.split("@");
        if (parts.length == 2) {
//            String[] sub_part = parts[1].split(".");
            if (parts[1].toLowerCase().equals("salon.com")) {
                loadingspin.setIsVisible(true);
                loadingspin.startAnimation();
                firebaseAuth.signInWithEmailAndPassword(et_email, et_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            prefsEditor.putString("salonmail", et_email);
                            prefsEditor.commit();
                            centralStore.getInstance().salonMail = mPrefs.getString("salonmail", "");
                            startActivity(new Intent(Login.this, OrderStatus.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Invalid login id or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                loadingspin.stopAnimation();
                loadingspin.setIsVisible(false);
                Toast.makeText(this, "Sorry! you don't have an business account", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            loadingspin.stopAnimation();
            loadingspin.setIsVisible(false);
            Toast.makeText(this, "Sorry! you don't have an business account", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}