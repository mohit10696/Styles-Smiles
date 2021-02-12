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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirmpassword)
    EditText et_confirmpassword;
    @BindView(R.id.et_firstname)
    EditText et_firstname;
    @BindView(R.id.et_lastname)
    EditText et_lastname;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences mPrefs;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    FirebaseDatabase rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        mDatabase = rootnode.getReference("user");
        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
    }

    public void loadSignin(View view) {
        finish();
        startActivity(new Intent(this, Login.class));
    }

    public void Signup(View view){
        if(et_email.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_password.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_confirmpassword.getText().toString().trim().isEmpty()  || !et_password.getText().toString().trim().equals(et_confirmpassword.getText().toString().trim())){
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(et_email.getText().toString().trim(),et_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    usermodel usermodel = new usermodel();
                    usermodel.setFirstname(et_firstname.getText().toString().trim());
                    usermodel.setLastname(et_lastname.getText().toString().trim());
                    usermodel.setEmail(et_email.getText().toString().trim());
                    centralStore.getInstance().user = usermodel;
                    mDatabase.child(usermodel.getEmail().replace('.','_')).setValue(usermodel);
                    Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    String json = gson.toJson(usermodel);
                    prefsEditor.putString("user", json);
                    prefsEditor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
                else{
                    Toast.makeText(SignUpActivity.this, task.getResult().describeContents(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}