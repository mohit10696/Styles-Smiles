package com.example.stylessmiles;

import com.example.stylessmiles.model.SaloonModel;
import com.example.stylessmiles.model.usermodel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class centralStore {

    private static centralStore mInstance;

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

    private static List<SaloonModel> saloons = new ArrayList<>();
    public static usermodel user = new usermodel();

    public static synchronized centralStore getInstance() {
        if (mInstance != null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            return mInstance;
        } else {
            return new centralStore();
        }
    }
}
