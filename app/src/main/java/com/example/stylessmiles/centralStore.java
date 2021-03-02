package com.example.stylessmiles;

import android.util.Log;

import com.example.stylessmiles.model.CartModel;
import com.example.stylessmiles.model.SaloonModel;
import com.example.stylessmiles.model.usermodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class centralStore {
    public static String salonMail = "";
    private static centralStore mInstance;
    public static  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:sss' '");
    public static List<SaloonModel> saloons = new ArrayList<>();
    public static usermodel user = new usermodel();
    public static CartModel cart = new CartModel();
    public static FirebaseDatabase rootnode;
    public static DatabaseReference mDatabase;
    public static synchronized centralStore getInstance() {
        if (mInstance != null) {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            return mInstance;
        } else {
            return new centralStore();
        }
    }

    public static String getSaloonImage(String salonname){

        for(int i = 0 ; i < saloons.size(); i++){
            Log.e(salonname, "getSaloonImage: "+saloons.get(i).getName() );
            if(saloons.get(i).getName().equals(salonname)){
                return saloons.get(i).getImgurl();
            }
        }
        return "https://www.thermaxglobal.com/wp-content/uploads/2020/05/image-not-found.jpg";
    }

    public static List<SaloonModel> getSaloons() {
        return saloons;
    }

    public static void setSaloons(List<SaloonModel> saloons) {
        centralStore.saloons = saloons;
    }

    public static usermodel getUser() {
        return user;
    }

    public static void setUser(usermodel user) {
        centralStore.user = user;
    }

    public static void synccart(){
        rootnode = FirebaseDatabase.getInstance();
        mDatabase = rootnode.getReference("user").child(getUser().getEmail().replace('.','_'));
        mDatabase.child("cart").setValue(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: "+"cart updated" );
            }
        });
    }

    public static String capitalize(String str)
    {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Date getDateobject(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    public static String getStringDate(Date date){
        return dateFormat.format(date);
    }

}
